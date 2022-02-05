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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")

    lateinit var post: StateFlow<Post>
    private val _comments = MutableStateFlow<Result<List<Comment>>>(Result.Loading())
    val comments: StateFlow<Result<List<Comment>>>
        get() = _comments
    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean>
        get() = _isDeleted
    private val _isEditClicked = MutableStateFlow(false)
    val isEditClicked: StateFlow<Boolean>
        get() = _isEditClicked

    init {
        postId?.let {
            viewModelScope.launch {
                post = repository.getPostFlow(postId).stateIn(viewModelScope)
                _comments.value = repository.getComments(postId)
            }
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            repository.deletePost(post.value).also { result ->
                if (result is Result.Success) _isDeleted.value = true
            }
        }
    }

    fun onClickEdit() {
        _isEditClicked.value = true
    }
}