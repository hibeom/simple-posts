package com.pinkcloud.simpleposts.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pinkcloud.simpleposts.R
import com.pinkcloud.simpleposts.databinding.EditDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditDialog : BottomSheetDialogFragment() {

    private lateinit var binding: EditDialogBinding
    private val viewModel: EditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditDialogBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.post.collect { post ->
                        post?.let {
                            binding.post = post
                        }
                    }
                }
                launch {
                    viewModel.isDone.collect { isDone ->
                        if (isDone) {
                            findNavController().popBackStack()
                        }
                    }
                }
                launch {
                    viewModel.errorMessage.collect { message ->
                        message?.let {
                            Toast.makeText(
                                context,
                                message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }
}