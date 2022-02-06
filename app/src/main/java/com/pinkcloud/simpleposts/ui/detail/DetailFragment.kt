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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pinkcloud.shared.remote.Result
import com.pinkcloud.simpleposts.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        val adapter = CommentsAdapter()
        binding.recyclerView.adapter = adapter
        binding.viewModel = viewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.post?.collectLatest { post ->
                        binding.post = post
                    }
                }
                launch {
                    viewModel.comments.collect { commentsResult ->
                        binding.loadingBar.isVisible = commentsResult is Result.Loading
                        binding.textError.isVisible = commentsResult is Result.Error
                        binding.textCommentTitle.isVisible = commentsResult !is Result.Error
                        if (commentsResult is Result.Success) {
                            adapter.submitList(commentsResult.data)
                        }
                    }
                }
                launch {
                    viewModel.isDeleted.collect { isDeleted ->
                        if (isDeleted) {
                            findNavController().popBackStack()
                        }
                    }
                }
                launch {
                    viewModel.isEditClicked.collect { isEditClicked ->
                        if (isEditClicked) {
                            findNavController().navigate(
                                DetailFragmentDirections.actionDetailFragmentToEditDialog(args.postId)
                            )
                            viewModel.editShown()
                        }
                    }
                }
            }
        }

        return binding.root
    }
}