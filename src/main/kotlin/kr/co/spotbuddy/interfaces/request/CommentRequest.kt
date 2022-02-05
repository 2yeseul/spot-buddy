package kr.co.spotbuddy.interfaces.request

data class CommentRequest(
    val comment: String,
    val isAnonymous: Boolean,
    val postId: Long,
    val isReply: Boolean,
    var parentCommentId: Long?,
)
