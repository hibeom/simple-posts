package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Named

class GetPostFlowUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(postId: Int) = postRepository.getPostFlow(postId)
}