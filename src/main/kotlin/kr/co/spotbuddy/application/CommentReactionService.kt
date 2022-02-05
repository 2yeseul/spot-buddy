package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.CommentReaction
import kr.co.spotbuddy.domain.CommentReactionType
import kr.co.spotbuddy.domain.repository.CommentReactionRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentReactionService(
    private val commentReactionRepository: CommentReactionRepository,
) {
    @Transactional
    fun saveReactionToComment(commentId: Long, memberId: Long, reactionType: CommentReactionType) {
        if (commentReactionRepository.existsByCommentIdAndMemberIdAndReactionType(commentId, memberId, reactionType)) {
            throw CustomException(ExceptionDefinition.ALREADY_EXISTS_COMMENT_REACTION)
        }

        val commentReaction = CommentReaction(
            id = null,
            commentId = commentId,
            memberId = memberId,
            reactionType = reactionType
        )

        commentReactionRepository.save(commentReaction)

        // TODO: fcm
    }

    @Transactional
    fun deleteCommentReaction(commentReactionId: Long, memberId: Long) {
        val commentReaction = commentReactionRepository.findByIdAndMemberId(commentReactionId, memberId)
            ?: throw CustomException(ExceptionDefinition.BAD_REQUEST)

        commentReactionRepository.deleteById(commentReactionId)
    }

    fun getAllByCommentId(commentId: Long): List<CommentReaction> {
        return commentReactionRepository.findAllByCommentId(commentId) ?: emptyList()
    }
}