package com.rehan.credandroidassignment.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rehan.credandroidassignment.api.CredAPI
import com.rehan.credandroidassignment.models.CredModel
import javax.inject.Inject

class CredRepository @Inject constructor(private val credAPI: CredAPI) {

    private val _credLiveData = MutableLiveData<CredModel>()
    val credLiveData: LiveData<CredModel>
        get() = _credLiveData

    suspend fun getCredResponse() {
        val response = credAPI.getSuccessCase()

        if (response.isSuccessful && response.body() != null) {
            _credLiveData.postValue(response.body())
        }else{
            return
        }
    }
}