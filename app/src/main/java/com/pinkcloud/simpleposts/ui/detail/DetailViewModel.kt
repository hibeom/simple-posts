package com.pinkcloud.simpleposts.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcloud.shared.data.PostRepository
import com.pinkcloud.shared.model.Comment
import com.pinkcloud.shared.model.Post
import com.pinkcloud.shared.remote.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")

    private val _post = MutableStateFlow<Result<Post>>(Result.Loading())
    val post: StateFlow<Result<Post>>
        get() = _post
    private val _comments = MutableStateFlow<Result<List<Comment>>>(Result.Loading())
    val comments: StateFlow<Result<List<Comment>>>
        get() = _comments

    init {
        postId?.let {
            viewModelScope.launch {
                _post.value = repository.getPost(postId)
                _comments.value = repository.getComments(postId)
            }
        }
    }
}