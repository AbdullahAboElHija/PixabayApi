package com.example.pixabayapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayapi.Model.Image
import com.example.pixabayapi.databinding.ActivityMainBinding
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var Imageslist :List<Image> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private val adapter = ShowAdapter(emptyList())
    var pageIdx = 20
    var page  = 1
    var isLoad : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // Observe changes in the imagesList LiveData
        viewModel.imagesList.observe(this) { images ->
            // Update UI or handle the response
            adapter.updateData(images)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                isLoad = true
                binding.progressBarMain.visibility = View.VISIBLE
                // Hide other UI elements if needed
            } else {
                isLoad = false
                binding.progressBarMain.visibility = View.GONE
                // Show other UI elements if needed
            }
        }
        viewModel.getImages(
            apiKey = "12175339-7048b7105116d7fa1da74220c",
            perPage = 10,
            searchText = "",
            Typing = false,
            scroll = false

        )
        binding.searchViewImages.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Update the search query as the user types
                page  = 1
                Log.w("CurrentPageModelView","$page")
                viewModel.getImages(
                    apiKey = "12175339-7048b7105116d7fa1da74220c",
                    perPage = 10,
                    searchText = newText.orEmpty(),
                    Typing = true,
                    scroll = false

                )
                return true
            }
        })




        val flexBoxLayoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }



        binding.rvImages.layoutManager = flexBoxLayoutManager
        binding.rvImages.adapter = adapter

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = flexBoxLayoutManager.childCount
                val totalItemCount = flexBoxLayoutManager.itemCount
                val firstVisibleItemPosition = flexBoxLayoutManager.findFirstVisibleItemPosition()

                // Check if we've reached the end of the list
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    if (!isLoad){
                        pageIdx  = pageIdx +5
                        Log.w("CurrentPageModelView","$page")
                        viewModel.getImages(
                            apiKey = "12175339-7048b7105116d7fa1da74220c",
                            perPage = pageIdx,
                            searchText = binding.searchViewImages.query.toString(),
                            Typing = false,
                            scroll = true

                        )
                    }



                }
            }
        }

        binding.rvImages.addOnScrollListener(scrollListener)


    }
}






