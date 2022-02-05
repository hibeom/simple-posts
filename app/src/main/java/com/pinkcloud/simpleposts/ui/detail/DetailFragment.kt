package com.pinkcloud.simpleposts.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.pinkcloud.shared.remote.Result
import com.pinkcloud.simpleposts.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: DetailFragmentBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.post.collect { postResult ->
                    binding.loadingBar.isVisible = postResult is Result.Loading
                    binding.textError.isVisible = postResult is Result.Error
                    if (postResult is Result.Success) {
                        binding.post = postResult.data
                    }
                }
            }
        }

        val adapter = CommentsAdapter()
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.comments.collect { commentsResult ->
                    binding.loadingBar.isVisible = commentsResult is Result.Loading
                    binding.textError.isVisible = commentsResult is Result.Error
                    binding.textCommentTitle.isVisible = commentsResult !is Result.Error
                    if (commentsResult is Result.Success) {
                        adapter.submitList(commentsResult.data)
                    }
                }
            }
        }

        return binding.root
    }
}