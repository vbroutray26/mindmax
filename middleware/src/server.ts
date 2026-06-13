import 'dotenv/config';
import express from 'express';
import { analysisRouter } from './routes/analysis';
import { modelsRouter } from './routes/models';
import { userRouter } from './routes/user';
import { authMiddleware } from './middleware/auth';
import { rateLimitMiddleware } from './middleware/rateLimit';

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json({ limit: '50kb' }));
app.use(express.urlencoded({ extended: true }));

// Health check (no auth)
app.get('/health', (_req, res) => {
  res.json({ status: 'ok', version: '1.0.0', app: 'Bernard/VB' });
});

// Protected API routes
app.use('/v1', authMiddleware);
app.use('/v1', rateLimitMiddleware);
app.use('/v1/analyse', analysisRouter);
app.use('/v1/models', modelsRouter);
app.use('/v1/user', userRouter);

// Global error handler
app.use((err: Error, _req: express.Request, res: express.Response, _next: express.NextFunction) => {
  console.error('[Error]', err.message);
  res.status(500).json({ error: 'Internal server error' });
});

app.listen(PORT, () => {
  console.log(`Bernard/VB middleware running on port ${PORT}`);
});

export default app;
