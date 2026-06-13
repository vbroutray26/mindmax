import * as admin from 'firebase-admin';

const FREE_TIER_MONTHLY_LIMIT = parseInt(process.env.FREE_TIER_MONTHLY_LIMIT ?? '5', 10);
const PRO_DAILY_LIMIT = 60;

const db = admin.firestore();

export async function checkQuota(userId: string, isPro: boolean): Promise<{ allowed: boolean; remaining: number }> {
  if (isPro) {
    const today = new Date().toISOString().split('T')[0];
    const ref = db.collection('usage').doc(`${userId}_${today}`);
    const doc = await ref.get();
    const count = doc.exists ? (doc.data()?.count ?? 0) : 0;
    return { allowed: count < PRO_DAILY_LIMIT, remaining: PRO_DAILY_LIMIT - count };
  }

  const month = new Date().toISOString().slice(0, 7); // YYYY-MM
  const ref = db.collection('usage').doc(`${userId}_${month}`);
  const doc = await ref.get();
  const count = doc.exists ? (doc.data()?.count ?? 0) : 0;
  return { allowed: count < FREE_TIER_MONTHLY_LIMIT, remaining: FREE_TIER_MONTHLY_LIMIT - count };
}

export async function incrementUsage(userId: string): Promise<void> {
  const isPro = false; // Resolved from token claims in caller
  const key = isPro
    ? `${userId}_${new Date().toISOString().split('T')[0]}`
    : `${userId}_${new Date().toISOString().slice(0, 7)}`;

  const ref = db.collection('usage').doc(key);
  await ref.set({ count: admin.firestore.FieldValue.increment(1) }, { merge: true });
}
