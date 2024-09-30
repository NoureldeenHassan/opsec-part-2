package com.birdhunt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BirdAdapter(private val birdList: List<Bird>) : RecyclerView.Adapter<BirdAdapter.BirdViewHolder>() {

    inner class BirdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageBird: ImageView = itemView.findViewById(R.id.imageBird)
        val textViewBirdName: TextView = itemView.findViewById(R.id.textViewBirdName)
        val textViewBirdDescription: TextView = itemView.findViewById(R.id.textViewBirdDescription)
        val textViewBirdLocation: TextView = itemView.findViewById(R.id.textViewBirdLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bird, parent, false)
        return BirdViewHolder(view)
    }

    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val bird = birdList[position]
        holder.textViewBirdName.text = bird.name
        holder.textViewBirdDescription.text = bird.description
        holder.textViewBirdLocation.text = bird.location

        // Here, you can load the bird image using Glide or Picasso if you have a URL
        // For example:
        // Glide.with(holder.imageBird.context).load(bird.url).into(holder.imageBird)
    }

    override fun getItemCount(): Int {
        return birdList.size
    }
}
