package com.platformcommons.cinefirst.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.platformcommons.cinefirst.R
import com.platformcommons.cinefirst.viewmodel.AddUserViewModel
import com.platformcommons.cinefirst.viewmodel.AddUserViewModelFactory
import com.platformcommons.cinefirst.workers.NetworkUtils
import com.platformcommons.cinefirst.workers.enqueueUserSyncWork
import kotlinx.coroutines.launch

class AddUserFragment : Fragment(R.layout.fragment_add_user) {

    private lateinit var viewModel: AddUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameInput = view.findViewById<EditText>(R.id.etName)
        val jobInput = view.findViewById<EditText>(R.id.etJob)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)


        val factory = AddUserViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[AddUserViewModel::class.java]

        val networkUtils = NetworkUtils(requireContext())

        btnSubmit.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val job = jobInput.text.toString().trim()
            val isOnline = networkUtils.isInternetAvailable()

            if (name.isNotEmpty() && job.isNotEmpty()) {
                viewModel.submitUser(name, job, isOnline)
                if (!isOnline) {
                    enqueueUserSyncWork(requireContext())
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.submissionState.collect { state ->
                    when (state) {
                        is AddUserViewModel.SubmissionState.Loading -> {
                            progressBar.visibility = View.VISIBLE

                        }
                        is AddUserViewModel.SubmissionState.Success -> {
                            Toast.makeText(requireContext(), "User submitted", Toast.LENGTH_SHORT).show()
                            navigateBackSafely()
                        }
                        is AddUserViewModel.SubmissionState.OfflineSaved -> {
                            Toast.makeText(requireContext(), "Saved offline. Will sync later.", Toast.LENGTH_SHORT).show()
                            navigateBackSafely()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun navigateBackSafely() {
        if (isAdded && findNavController().currentDestination?.id == R.id.addUserFragment) {
            findNavController().navigate(
                R.id.action_addUserFragment_to_userListFragment,
                null,
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.userListFragment, false)
                    .build()
            )
        }
    }
}
