package com.example.ecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState =
        MutableStateFlow<AuthState>(if (auth.currentUser != null) AuthState.Success(auth.currentUser!!.uid) else AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    val isLoggedIn: Boolean get() = authState.value is AuthState.Success

    // Getting current user
    val currentUser = auth.currentUser?.let {
        UserProfile(
            uid = it.uid,
            name = it.displayName ?: "",
            email = it.email ?: "",
        )
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                result.user?.let { _authState.value = AuthState.Success(it.uid) }
                    ?: run { _authState.value = AuthState.Error("Login failed!") }

            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Login failed!")
            }
        }
    }

    fun signUp(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Success("SignUp Success!")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "SignUp failed!")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}