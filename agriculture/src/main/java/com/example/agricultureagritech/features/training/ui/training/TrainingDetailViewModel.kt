// This file manages UI-related data for the training detail screens.
package com.example.agricultureagritech.features.training.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agricultureagritech.core.domain.model.TrainingConcept
import com.example.agricultureagritech.core.network.RetrofitClient
import com.example.agricultureagritech.features.training.domain.repository.TrainingRepository
import kotlinx.coroutines.launch

/*
* This ViewModel fetches and holds the data for a single training concept.
* It uses viewModelScope to run the network call on a background thread.
*/
class TrainingDetailViewModel : ViewModel() {
    private val repository = TrainingRepository(RetrofitClient.apiService)

    private val _trainingConcept = MutableLiveData<TrainingConcept>()
    val trainingConcept: LiveData<TrainingConcept> = _trainingConcept

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchTrainingData(conceptKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getTrainingConcept(conceptKey)
                if (response.isSuccessful) {
                    _trainingConcept.postValue(response.body())
                } else {
                    _error.postValue("API Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Network Failure: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}