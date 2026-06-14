package com.bernardvb.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bernardvb.data.repository.UserProgressRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Success(val uid: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    val isLoggedIn: Boolean get() = auth.currentUser != null

    fun signInWithEmail(email: String, password: String) {
        _state.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                viewModelScope.launch {
                    userProgressRepository.initProgress(uid)
                    _state.value = AuthState.Success(uid)
                }
            }
            .addOnFailureListener { _state.value = AuthState.Error(it.message ?: "Sign in failed") }
    }

    fun createAccount(email: String, password: String) {
        _state.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                viewModelScope.launch {
                    userProgressRepository.initProgress(uid)
                    _state.value = AuthState.Success(uid)
                }
            }
            .addOnFailureListener { _state.value = AuthState.Error(it.message ?: "Account creation failed") }
    }

    fun signInWithGoogle(idToken: String) {
        _state.value = AuthState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener
                viewModelScope.launch {
                    userProgressRepository.initProgress(uid)
                    _state.value = AuthState.Success(uid)
                }
            }
            .addOnFailureListener { _state.value = AuthState.Error(it.message ?: "Google sign in failed") }
    }

    fun clearError() { _state.value = AuthState.Idle }
}
