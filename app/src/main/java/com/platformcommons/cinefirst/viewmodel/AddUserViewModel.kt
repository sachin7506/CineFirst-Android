package com.platformcommons.cinefirst.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.platformcommons.cinefirst.model.AddUserRequest
import com.platformcommons.cinefirst.model.PendingUser
import com.platformcommons.cinefirst.repository.AddUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddUserViewModel(private val repository: AddUserRepository) : ViewModel() {

    private val _submissionState = MutableStateFlow<SubmissionState>(SubmissionState.Idle)
    val submissionState: StateFlow<SubmissionState> = _submissionState

    fun submitUser(name: String, job: String, isOnline: Boolean) {
        viewModelScope.launch {
            _submissionState.value = SubmissionState.Loading
            val user = PendingUser(name = name, job = job)
            try {
                if (isOnline) {
                    repository.submitUserOnline(AddUserRequest(name, job))
                    _submissionState.value = SubmissionState.Success
                } else {
                    repository.saveUserOffline(user)
                    _submissionState.value = SubmissionState.OfflineSaved
                }
            } catch (e: Exception) {
                repository.saveUserOffline(user)
                _submissionState.value = SubmissionState.OfflineSaved
            }
        }
    }

    sealed class SubmissionState {
        object Idle : SubmissionState()
        object Loading : SubmissionState()
        object Success : SubmissionState()
        object OfflineSaved : SubmissionState()
    }
}
