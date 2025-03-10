package com.example.smarttextvision.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    // StateFlow for authentication state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // StateFlow for current user
    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser

    // Sealed class for authentication states
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val userId: String? = null) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    // Sealed class for operation results (e.g., admin actions)
    sealed class OperationResult<out T> {
        data class Success<out T>(val data: T) : OperationResult<T>()
        data class Error(val message: String) : OperationResult<Nothing>()
        object Unauthorized : OperationResult<Nothing>()
        object Loading : OperationResult<Nothing>()
    }

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            try {
                val email = getCurrentUserEmail()
                _currentUser.value = userRepository.getUserByEmail(email)
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error loading current user", e)
                _currentUser.value = null
            }
        }
    }

    private fun getCurrentUserEmail(): String {
        return "current@email.com" // Replace with actual session management
    }

    private fun isAdmin(): Boolean {
        return currentUser.value?.role == "admin"
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                // Simulate authentication (replace with real repository logic)
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val user = userRepository.getUserByEmail(email)
                    if (user != null && user.password == password) { // Dummy check; use hashing in production
                        _authState.value = AuthState.Success(userId = user.id.toString())
                    } else {
                        _authState.value = AuthState.Error("Invalid email or password")
                    }
                } else {
                    _authState.value = AuthState.Error("Email and password are required")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign-in error", e)
                _authState.value = AuthState.Error("Sign-in failed: ${e.message}")
            }
        }
    }

    fun deleteUser(userId: Int): Flow<OperationResult<Unit>> = flow {
        emit(OperationResult.Loading)
        if (!isAdmin()) {
            emit(OperationResult.Unauthorized)
            return@flow
        }
        try {
            val userToDelete = userRepository.getUserById(userId)
            if (userToDelete != null) {
                userRepository.deleteUser(userToDelete)
                emit(OperationResult.Success(Unit))
            } else {
                emit(OperationResult.Error("User not found"))
            }
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Error deleting user", e)
            emit(OperationResult.Error("Failed to delete user: ${e.message}"))
        }
    }

    fun readAllChats(): LiveData<OperationResult<List<MessageEntity>>> {
        val result = MutableLiveData<OperationResult<List<MessageEntity>>>()
        result.value = OperationResult.Loading
        viewModelScope.launch {
            if (!isAdmin()) {
                result.postValue(OperationResult.Unauthorized)
                return@launch
            }
            try {
                val messages = chatRepository.getAllMessages()
                result.postValue(OperationResult.Success(messages))
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error reading all chats", e)
                result.postValue(OperationResult.Error("Failed to load chats: ${e.message}"))
            }
        }
        return result
    }

    fun refreshCurrentUser() {
        loadCurrentUser()
    }

    fun signOut() {
        _authState.value = AuthState.Idle
        _currentUser.value = null
    }
}