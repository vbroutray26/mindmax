# Firebase Setup — Bernard/VB

## Local development (emulator — no Firebase account needed)

### Prerequisites
```bash
npm install -g firebase-tools
firebase login         # authenticate with your Google account
```

### Start emulators
```bash
# From project root
firebase emulators:start
```
This starts Auth on :9099, Firestore on :8080, and the Emulator UI on :4000.

The Android app and middleware both detect `DEBUG` mode and connect to these automatically.

### Run middleware against emulators
```bash
cd middleware
npm run dev:emulator
```

---

## Production Firebase setup

1. Go to [console.firebase.google.com](https://console.firebase.google.com)
2. Create a project — suggested ID: `bernardvb-prod`
3. Under **Authentication → Sign-in method**, enable **Email/Password**
4. Under **Firestore Database**, create a database in production mode
5. Deploy security rules:
   ```bash
   firebase use production
   firebase deploy --only firestore:rules,firestore:indexes
   ```
6. Go to **Project settings → Your apps → Add app → Android**
   - Package name: `com.bernardvb.app`
   - Download `google-services.json` and place it at `app/google-services.json`
   - (It is gitignored — never commit it)
7. For the middleware, create a **service account**:
   - Project settings → Service accounts → Generate new private key
   - Save as `middleware/service-account.json` (also gitignored)
   - Set `GOOGLE_APPLICATION_CREDENTIALS=./service-account.json` in `middleware/.env`

### Update `.firebaserc`
```json
{
  "projects": {
    "default": "bernardvb-dev",
    "production": "bernardvb-prod"
  }
}
```

### Switching environments
```bash
firebase use default     # local emulators
firebase use production  # prod project
```

---

## Files reference

| File | Committed | Purpose |
|---|---|---|
| `app/google-services.json` | ❌ gitignored | Real Firebase credentials (prod/dev project) |
| `app/google-services.json.template` | ✅ | Structure reference |
| `middleware/.env` | ❌ gitignored | API keys and service account path |
| `middleware/.env.example` | ✅ | Variable names reference |
| `middleware/service-account.json` | ❌ gitignored | Firebase Admin SDK key |
| `firebase.json` | ✅ | Emulator port config |
| `firestore.rules` | ✅ | Security rules (deploy to prod) |
| `firestore.indexes.json` | ✅ | Composite indexes |
