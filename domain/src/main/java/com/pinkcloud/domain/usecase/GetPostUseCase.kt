package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.repository.PostRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int) = postRepository.getPost(postId)
}