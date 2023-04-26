package com.example.roomyweather.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomyweather.data.*
import kotlinx.coroutines.launch

class SavedDataRepoViewModel(application: Application): AndroidViewModel(application) {
    private val repository = SavedDataRepo(
        AppDatabase.getInstance(application).ForecastDao()
    )

    val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()

    fun addBookmarkedRepo(repo: FiveDayForecastEntity) {
        viewModelScope.launch {
            repository.insertBookmarkedRepo(repo)
        }
    }

    fun removeBookmarkedRepo(repo: FiveDayForecastEntity) {
        viewModelScope.launch {
            repository.deleteBookmarkedRepo(repo)
        }
    }

    fun getBookmarkedRepoByName(city: String) =
        repository.getBookmarkedRepoByName(city).asLiveData()

    fun isRepoSaved(city: String): Boolean {
        val repoLiveData = repository.getBookmarkedRepoByName(city).asLiveData()
        return repoLiveData.value != null
    }
}