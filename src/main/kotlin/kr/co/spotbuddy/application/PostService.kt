package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Post
import kr.co.spotbuddy.domain.repository.PostQueryRepository
import kr.co.spotbuddy.domain.repository.PostRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.PostRequest
import kr.co.spotbuddy.interfaces.response.PostView
import kr.co.spotbuddy.util.BadWordsUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val postQueryRepository: PostQueryRepository,
    private val memberService: MemberService,
    private val postFileService: PostFileService,
    private val commentReactionService: CommentReactionService,
) {
    fun getPostById(postId: Long): Post =
        postRepository.findByIdOrNull(postId) ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

    @Transactional
    fun savePost(memberId: Long, postRequest: PostRequest): Long {
        val member = memberService.getById(memberId)
        val post = Post.of(member, postRequest)

        postFileService.savePostFiles(post, postRequest.files)

        return post.id!!
    }

    // TODO: Implement
    fun findAllPostsByMemberId(memberId: Long) {
        val posts = postQueryRepository.findAllByMemberId(memberId)

    }

    fun checkBadWordsInContent(postRequest: PostRequest): Boolean =
        BadWordsUtil.isBadWordsContains(postRequest.content) || BadWordsUtil.isBadWordsContains(postRequest.title)

}