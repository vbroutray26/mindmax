package com.bernardvb

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class BernardVBMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // TODO: send token to backend for push targeting
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Handle check-in reminders, streak alerts, weekly challenge notifications
        val type = message.data["type"] ?: return
        when (type) {
            "check_in_reminder" -> handleCheckInReminder(message)
            "streak_reminder" -> handleStreakReminder(message)
            "weekly_challenge" -> handleWeeklyChallenge(message)
        }
    }

    private fun handleCheckInReminder(message: RemoteMessage) { /* TODO */ }
    private fun handleStreakReminder(message: RemoteMessage) { /* TODO */ }
    private fun handleWeeklyChallenge(message: RemoteMessage) { /* TODO */ }
}
