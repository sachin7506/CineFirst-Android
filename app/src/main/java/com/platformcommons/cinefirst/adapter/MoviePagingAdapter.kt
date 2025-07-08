package com.platformcommons.cinefirst.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.platformcommons.cinefirst.R
import com.platformcommons.cinefirst.model.Movie

class MoviePagingAdapter(
    private val onItemClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
        holder.itemView.setOnClickListener { onItemClick(movie) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.ivPoster)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val date: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(movie: Movie) {
            title.text = movie.title
            date.text = movie.release_date
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(poster)
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}
