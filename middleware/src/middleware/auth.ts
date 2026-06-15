import { Request, Response, NextFunction } from 'express';
import * as admin from 'firebase-admin';

// Extend Request to include user
declare global {
  namespace Express {
    interface Request {
      user?: admin.auth.DecodedIdToken;
    }
  }
}

// Initialise Firebase Admin once.
// In production (Render/Railway) pass the service account as a base64 string:
//   FIREBASE_SERVICE_ACCOUNT_B64=$(base64 -i service-account.json | tr -d '\n')
// In local dev, set GOOGLE_APPLICATION_CREDENTIALS to the file path instead.
if (!admin.apps.length) {
  if (process.env.FIREBASE_SERVICE_ACCOUNT_B64) {
    const serviceAccount = JSON.parse(
      Buffer.from(process.env.FIREBASE_SERVICE_ACCOUNT_B64, 'base64').toString('utf8')
    );
    admin.initializeApp({ credential: admin.credential.cert(serviceAccount) });
  } else {
    admin.initializeApp({ credential: admin.credential.applicationDefault() });
  }
}

export async function authMiddleware(req: Request, res: Response, next: NextFunction): Promise<void> {
  const authHeader = req.headers.authorization;
  if (!authHeader?.startsWith('Bearer ')) {
    res.status(401).json({ error: 'Missing or invalid Authorization header' });
    return;
  }

  const token = authHeader.split('Bearer ')[1];
  try {
    const decoded = await admin.auth().verifyIdToken(token);
    req.user = decoded;
    next();
  } catch {
    res.status(401).json({ error: 'Invalid or expired token' });
  }
}
