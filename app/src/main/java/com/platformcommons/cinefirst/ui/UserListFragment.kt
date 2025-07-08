package com.platformcommons.cinefirst.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.platformcommons.cinefirst.R
import com.platformcommons.cinefirst.adapter.UserPagingAdapter
import com.platformcommons.cinefirst.viewmodel.UserListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvUsers)
        val tvError = view.findViewById<TextView>(R.id.tvError)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAddUser)

        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        adapter = UserPagingAdapter {
            if (isAdded && findNavController().currentDestination?.id == R.id.userListFragment) {
                findNavController().navigate(R.id.action_userListFragment_to_movieListFragment)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isConnected.collect { connected ->
                    if (connected) {
                        tvError.visibility = View.GONE
                        adapter.refresh()
                    } else {
                        tvError.text = "No internet connection"
                        tvError.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagedUsers.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
        }

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }
    }
}
