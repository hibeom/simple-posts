package com.pinkcloud.simpleposts.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcloud.data.source.PostRepository
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")
    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?>
        get() = _post
    private val _isDone = MutableStateFlow(false)
    val isDone: StateFlow<Boolean>
        get() = _isDone
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    init {
        postId?.let {
            viewModelScope.launch {
                _post.value = repository.getPost(postId).let { result ->
                    if (result is Result.Success) result.data
                    else null
                }
            }
        }
    }

    fun updatePost(title: String, body: String) {
        viewModelScope.launch {
            val newPost = Post(
                id = post.value!!.id,
                userId = post.value!!.userId,
                title = title,
                body = body
            )
            repository.updatePost(newPost).also { result ->
                if (result is Result.Success) {
                    _isDone.value = true
                } else {
                    _errorMessage.value = result.message
                }
            }
        }
    }
}