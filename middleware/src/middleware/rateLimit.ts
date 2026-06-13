import rateLimit from 'express-rate-limit';

// 10 requests per minute per IP / user
export const rateLimitMiddleware = rateLimit({
  windowMs: 60 * 1000,
  max: 10,
  standardHeaders: true,
  legacyHeaders: false,
  keyGenerator: (req) => req.user?.uid ?? req.ip ?? 'unknown',
  message: { error: 'Too many requests. Slow down.' },
  skip: (req) => req.path === '/health',
});
