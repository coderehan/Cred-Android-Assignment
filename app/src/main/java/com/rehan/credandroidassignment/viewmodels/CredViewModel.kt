package com.rehan.credandroidassignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rehan.credandroidassignment.repositories.CredRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CredViewModel @Inject constructor(private val credRepository: CredRepository): ViewModel() {

    val credLiveData get() = credRepository.credLiveData

    fun getCredResponse() {
        viewModelScope.launch {
            credRepository.getCredResponse()
        }
    }
}