package com.example.pixabayapi

import android.util.Log
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
    var isLoading = MutableLiveData(false)
    fun getImages(apiKey: String, perPage: Int, searchText : String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.retrofit.create(Api::class.java).getImages(
                    apiKey = apiKey,
                    perPage = perPage,
                    searchText = searchText
                )

                // Process the response
                withContext(Dispatchers.Main) {
                    // Update LiveData with the response

                    _imagesList.value = response.hits
                    val imagelist = response.hits
                    imagelist.map { it -> Log.w("ImagePrintList","$it") }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                // Handle errors
            }
        }
    }
}