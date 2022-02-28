package kr.co.spotbuddy.interfaces.request

import kr.co.spotbuddy.domain.CommentReaction
import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.Post

data class PushAlarmRequest(
    val token: String,
    val title: String,
    val body: String,
    val path: String,
)

data class PushAlarmTarget(
    val to: Member,
    val from: Member,
    val commentReaction: CommentReaction?,
    val post: Post?,
    val type: PushAlarmType
)

enum class PushAlarmType {
    COMMENT_REACTION,
    ETC,
}