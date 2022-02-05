package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.CommentReaction
import kr.co.spotbuddy.domain.CommentReactionType
import org.springframework.data.jpa.repository.JpaRepository

interface CommentReactionRepository: JpaRepository<CommentReaction, Long> {
    fun existsByCommentIdAndMemberIdAndReactionType(commentId: Long, memberId: Long, reactionType: CommentReactionType): Boolean
    fun findAllByCommentId(commentId: Long): List<CommentReaction>?
    fun findByIdAndMemberId(id: Long, memberId: Long): CommentReaction?
}