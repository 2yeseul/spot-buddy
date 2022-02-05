package kr.co.spotbuddy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kr.co.spotbuddy.domain.Comment
import kr.co.spotbuddy.domain.CommentReaction
import kr.co.spotbuddy.domain.Post
import kr.co.spotbuddy.domain.repository.CommentRepository
import kr.co.spotbuddy.interfaces.request.CommentRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val memberService: MemberService,
    private val commentReactionService: CommentReactionService,
    private val postService: PostService,
) {
    @Transactional
    fun saveComment(memberId: Long, commentRequest: CommentRequest) {
        val member = memberService.getById(memberId)
        val comment = Comment.of(member, commentRequest)

        commentRepository.save(comment)
        // TODO: FCM 보내야함
    }

    suspend fun getAllCommentsByPostId(postId: Long): Triple<List<Comment>, Map<Long, List<CommentReaction>>, Post> {
        val post = postService.getPostById(postId)
        val comments = commentRepository.findAllByPostId(postId)

        val reactionsMap = HashMap<Long, List<CommentReaction>>()

        if (comments.isNotEmpty()) {
            comments.forEach {
                val reactions = CoroutineScope(Dispatchers.IO).async {
                    commentReactionService.getAllByCommentId(it.id!!)
                }

                reactionsMap[it.id!!] = reactions.await()
            }

        }
        return Triple(comments, reactionsMap, post)
    }
}