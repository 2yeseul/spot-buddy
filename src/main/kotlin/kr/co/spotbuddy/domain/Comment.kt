package kr.co.spotbuddy.domain

import kr.co.spotbuddy.interfaces.request.CommentRequest
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    val isAnonymous: Boolean,

    val createdAt: LocalDateTime,

    val comment: String,

    val isReply: Boolean,

    var parentCommentId: Long?,

    val postId: Long,

    var deletedAt: LocalDateTime?,
    val isDeleted: Boolean = false,
) {
    companion object {
        fun of(member: Member, commentRequest: CommentRequest): Comment {
            return Comment(
                id = null,
                member = member,
                isAnonymous = commentRequest.isAnonymous,
                createdAt = LocalDateTime.now(),
                comment = commentRequest.comment,
                isReply = commentRequest.isReply,
                parentCommentId = commentRequest.parentCommentId,
                postId = commentRequest.postId,
                deletedAt = null,
            )
        }
    }
}