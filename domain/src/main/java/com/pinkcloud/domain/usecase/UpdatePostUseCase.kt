package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.repository.PostRepository
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post) = postRepository.updatePost(post)
}