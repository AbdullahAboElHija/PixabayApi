package com.example.pixabayapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pixabayapi.Api.Api
import com.example.pixabayapi.Api.RetrofitInstance
import com.example.pixabayapi.Model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel :ViewModel(){
    private val _imagesList = MutableLiveData<List<Image>>()
    val imagesList: LiveData<List<Image>> get() = _imagesList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var currentPage = 1
    fun getImages(apiKey: String, perPage: Int, searchText : String,Typing : Boolean ,scroll:Boolean) {
        _isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if(scroll){
                    currentPage++
                }
                else{currentPage = 1}
                val response = RetrofitInstance.retrofit.create(Api::class.java).getImages(
                    apiKey = apiKey,
                    perPage = perPage,
                    page = currentPage,
                    searchText = searchText

                )

                // Process the response
                withContext(Dispatchers.Main) {
                    // Update LiveData with the response

                        val currentList = _imagesList.value?.toMutableList() ?: mutableListOf()
                        if (Typing){
                            currentList.clear()
                        }
                        currentList.addAll(response.hits)
                        _imagesList.value = currentList

                    _isLoading.value = false


                }

            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors
            }
        }
    }
}