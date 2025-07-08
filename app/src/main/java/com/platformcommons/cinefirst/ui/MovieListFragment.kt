package com.platformcommons.cinefirst.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.platformcommons.cinefirst.adapter.MoviePagingAdapter
import com.platformcommons.cinefirst.viewmodel.MovieViewModel
import com.platformcommons.cinefirst.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.Lifecycle
import com.platformcommons.cinefirst.R

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MoviePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.rvMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val factory = MovieViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]


        adapter = MoviePagingAdapter { movie ->
            val action = MovieListFragmentDirections
                .actionMovieListFragmentToMovieDetailFragment(movie.id)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}
