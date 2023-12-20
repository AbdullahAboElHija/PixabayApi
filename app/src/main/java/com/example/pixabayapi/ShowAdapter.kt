package com.example.pixabayapi



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayapi.Model.Image
import com.squareup.picasso.Picasso

class ShowAdapter(private var images: List<Image>) :
    RecyclerView.Adapter<ShowAdapter.ToShowViewHolder>() {

    inner class ToShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewImplementation)
        private val imageLove: ImageView = itemView.findViewById(R.id.imageLoveEmpty)

        fun bind(image: Image) {
            var flag = 1
            val uriString = image.largeImageURL
            if (uriString != null && uriString.isNotEmpty()) {
                // Use Picasso to load the image into the ImageView
                Picasso.get()
                    .load(uriString)
                    .into(imageView)
            }

            imageLove.setOnClickListener(){
                if (flag == 1) {
                    var newImageResource = R.drawable.lovefulll
                    imageLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            newImageResource
                        )
                    )
                    flag = flag*-1
                }else{
                    var newImageResource = R.drawable.love
                    imageLove.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            newImageResource
                        )
                    )
                    flag = flag*-1

                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_in_page, parent, false)
        return ToShowViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ToShowViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun updateData(newImages: List<Image>) {
        images = newImages
        notifyDataSetChanged()
    }
}
