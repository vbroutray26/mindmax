import { Router, Request, Response } from 'express';
import { z } from 'zod';
import { runAnalysis } from '../services/anthropicService';
import { checkQuota, incrementUsage } from '../services/quotaService';
import { getModelById, getModelsByIds } from '../services/modelService';
import * as admin from 'firebase-admin';

export const analysisRouter = Router();

const db = admin.firestore();

const AnalysisRequestSchema = z.object({
  situationText: z.string().min(20, 'Situation too short').max(3000, 'Situation too long'),
  moodContext: z.string().optional(),
  urgencyContext: z.string().optional(),
  deepAnalysis: z.boolean().default(false),
  previousModelIds: z.array(z.string()).default([]),
});

analysisRouter.post('/', async (req: Request, res: Response): Promise<void> => {
  const parsed = AnalysisRequestSchema.safeParse(req.body);
  if (!parsed.success) {
    res.status(400).json({ error: parsed.error.issues[0]?.message });
    return;
  }

  const { situationText, moodContext, urgencyContext, deepAnalysis, previousModelIds } = parsed.data;
  const userId = req.user!.uid;
  const isPro = req.user!.isPro as boolean ?? false;

  // Check quota
  const quota = await checkQuota(userId, isPro);
  if (!quota.allowed) {
    res.status(402).json({
      error: 'Monthly analysis limit reached',
      upgradeRequired: true,
      usageRemaining: 0,
    });
    return;
  }

  try {
    // Run AI analysis
    const aiResult = await runAnalysis({
      situationText,
      moodContext,
      urgencyContext,
      deepAnalysis,
      previousModelIds,
    });

    // Enrich with full model data
    const primaryModel = getModelById(aiResult.primaryModelId);
    if (!primaryModel) throw new Error(`Unknown model: ${aiResult.primaryModelId}`);

    const secondaryModels = getModelsByIds(aiResult.secondaryModelIds);
    const blindSpotModel = aiResult.blindSpotModelId ? getModelById(aiResult.blindSpotModelId) : null;

    const analysisId = db.collection('analyses').doc().id;
    const xpEarned = deepAnalysis ? 30 : 20;

    const responseBody = {
      analysisId,
      primaryModel: {
        id: primaryModel.id,
        name: primaryModel.name,
        origin: primaryModel.origin,
        shortDesc: primaryModel.shortDesc,
        contextApplication: aiResult.primaryContextApplication,
      },
      secondaryModels: secondaryModels.map((m, i) => ({
        id: m.id,
        name: m.name,
        reason: aiResult.secondaryReasons[i] ?? '',
      })),
      blindSpot: blindSpotModel ? {
        id: blindSpotModel.id,
        name: blindSpotModel.name,
        alertText: aiResult.blindSpotText ?? '',
      } : null,
      decisionPaths: {
        conservative: aiResult.conservativePath,
        balanced: aiResult.balancedPath,
        bold: aiResult.boldPath,
      },
      deepSynthesis: aiResult.deepSynthesis ?? null,
      xpEarned,
      usageRemaining: isPro ? null : quota.remaining - 1,
    };

    // Persist to Firestore (non-blocking)
    db.collection('analyses').doc(analysisId).set({
      userId,
      ...responseBody,
      createdAt: admin.firestore.FieldValue.serverTimestamp(),
    }).catch(console.error);

    await incrementUsage(userId);

    res.json(responseBody);
  } catch (error) {
    console.error('[Analysis Error]', error);
    res.status(500).json({ error: 'Analysis failed. Please try again.' });
  }
});
