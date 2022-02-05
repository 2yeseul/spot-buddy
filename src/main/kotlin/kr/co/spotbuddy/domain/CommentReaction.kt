package kr.co.spotbuddy.domain

import javax.persistence.*

@Entity
data class CommentReaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    val memberId: Long,

    val commentId: Long,

    @Enumerated(EnumType.STRING)
    val reactionType: CommentReactionType,
) {
}

enum class CommentReactionType {
    LIKE,
    DISLIKE,
}

typealias CommentReactionView = CommentReaction