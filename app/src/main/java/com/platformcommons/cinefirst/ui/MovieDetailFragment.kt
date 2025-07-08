package com.platformcommons.cinefirst.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.platformcommons.cinefirst.BuildConfig.TMDB_API_KEY
import com.platformcommons.cinefirst.R
import com.platformcommons.cinefirst.remote.RetrofitMovieClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)

        val ivPoster = view.findViewById<ImageView>(R.id.ivPoster)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val tvOverview = view.findViewById<TextView>(R.id.tvOverview)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val detail = RetrofitMovieClient.apiService.getMovieDetail(args.movieId, TMDB_API_KEY)

                withContext(Dispatchers.Main) {
                    tvTitle.text = detail.title
                    tvDate.text = "Release Date: ${detail.release_date}"
                    tvOverview.text = detail.overview

                    val imageUrl = "https://image.tmdb.org/t/p/w185${detail.poster_path}"
                    Glide.with(requireContext()).load(imageUrl).into(ivPoster)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
}
