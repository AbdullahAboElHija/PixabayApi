package com.example.pixabayapi

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    var perPageImages = 20

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
                binding.progressBarMain.visibility = View.VISIBLE
                // Hide other UI elements if needed
            } else {
                binding.progressBarMain.visibility = View.GONE
                // Show other UI elements if needed
            }
        }
        viewModel.getImages(
            apiKey = "12175339-7048b7105116d7fa1da74220c",
            perPage = 20,
            searchText = ""
        )
        binding.searchViewImages.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Update the search query as the user types
                viewModel.getImages(
                    apiKey = "12175339-7048b7105116d7fa1da74220c",
                    perPage = 25,
                    searchText = newText.orEmpty()
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

//        val scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val visibleItemCount = flexBoxLayoutManager.childCount
//                val totalItemCount = flexBoxLayoutManager.itemCount
//                val firstVisibleItemPosition = flexBoxLayoutManager.findFirstVisibleItemPosition()
//
//                // Check if we've reached the end of the list
//                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                ) {
//                    perPageImages=perPageImages+3
//                    viewModel.getImages(
//                        apiKey = "12175339-7048b7105116d7fa1da74220c",
//                        perPage = perPageImages,
//                        searchText = binding.searchViewImages.toString()
//                    )
//                }
//            }
//        }

//        binding.rvImages.addOnScrollListener(scrollListener)


    }
}




