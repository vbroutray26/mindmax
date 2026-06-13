import { Router, Request, Response } from 'express';
import * as admin from 'firebase-admin';

export const userRouter = Router();

const db = admin.firestore();

userRouter.post('/progress/sync', async (req: Request, res: Response): Promise<void> => {
  const userId = req.user!.uid;
  try {
    await db.collection('progress').doc(userId).set(
      { ...req.body, updatedAt: admin.firestore.FieldValue.serverTimestamp() },
      { merge: true }
    );
    res.json({ synced: true, serverTimestamp: Date.now() });
  } catch {
    res.status(500).json({ error: 'Sync failed' });
  }
});

userRouter.post('/journal/:entryId/checkin', async (req: Request, res: Response): Promise<void> => {
  const { entryId } = req.params;
  const { rating, reflection } = req.body;
  const userId = req.user!.uid;

  if (!rating || typeof rating !== 'number' || rating < 1 || rating > 5) {
    res.status(400).json({ error: 'Rating must be 1–5' });
    return;
  }

  try {
    const ref = db.collection('journal').doc(`${userId}_${entryId}`);
    const doc = await ref.get();
    const current = doc.data()?.decisionAccuracyScore ?? 0.5;
    const delta = (rating - 3) * 0.02;
    const newScore = Math.max(0, Math.min(1, current + delta));

    await ref.set({ rating, reflection, updatedAt: admin.firestore.FieldValue.serverTimestamp() }, { merge: true });
    await db.collection('progress').doc(userId).set(
      { decisionAccuracyScore: newScore },
      { merge: true }
    );

    res.json({ decisionAccuracyDelta: delta, newAccuracyScore: newScore });
  } catch {
    res.status(500).json({ error: 'Check-in failed' });
  }
});
