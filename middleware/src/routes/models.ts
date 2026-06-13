import { Router, Request, Response } from 'express';
import { getAllModels, getFreeModels } from '../services/modelService';

export const modelsRouter = Router();

modelsRouter.get('/', (req: Request, res: Response): void => {
  const since = req.query.since ? parseInt(req.query.since as string, 10) : null;
  const tier = (req.query.tier as string) ?? 'free';
  const isPro = tier === 'pro';

  const all = isPro ? getAllModels() : getFreeModels();

  res.json({
    models: all,
    totalCount: all.length,
    lastUpdated: Date.now(),
  });
});
