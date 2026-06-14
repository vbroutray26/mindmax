package com.bernardvb

import android.app.Application
import com.bernardvb.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BernardVBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // Point to local Firebase Emulator Suite.
            // Run `firebase emulators:start` in the project root before launching the app.
            // Android Emulator uses 10.0.2.2 to reach the host machine's localhost.
            FirebaseAuth.getInstance().useEmulator("10.0.2.2", 9099)
            FirebaseFirestore.getInstance().useEmulator("10.0.2.2", 8080)
        }
    }
}
