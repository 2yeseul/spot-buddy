package kr.co.spotbuddy.interfaces.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.co.spotbuddy.domain.Comment
import kr.co.spotbuddy.domain.CommentReaction
import kr.co.spotbuddy.domain.CommentReactionView
import kr.co.spotbuddy.domain.Post
import java.time.LocalDateTime

data class CommentView(
    val commentId: Long,
    val nickname: String,
    val reactions: List<CommentReactionView>,
    val isAnonymous: Boolean,

    val comment: String,

    val isReply: Boolean,
    var parentCommentId: Long?,
    val teamIndex: Long,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post, comment: Comment, reactions: List<CommentReaction>): CommentView {
            val filteredComment = if (comment.isDeleted) "삭제된 댓글입니다." else comment.comment
            val nickname = if (comment.isAnonymous) "익명" else comment.member.nickname

            return CommentView(
                commentId = comment.id!!,
                nickname = nickname,
                reactions = reactions,
                isAnonymous = comment.isAnonymous,
                comment = comment.comment,
                isReply = comment.isReply,
                parentCommentId = comment.parentCommentId,
                teamIndex = post.teamIndex,
                createdAt = comment.createdAt
            )
        }
    }
}
