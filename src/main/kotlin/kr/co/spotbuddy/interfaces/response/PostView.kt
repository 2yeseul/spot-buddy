package kr.co.spotbuddy.interfaces.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.co.spotbuddy.domain.Post
import kr.co.spotbuddy.domain.PostFile
import java.time.LocalDateTime

data class PostView(
    val id: Long,
    val nickname: String,
    val isAnonymous: Boolean,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val createdAt: LocalDateTime,

    val title: String,
    val content: String,

    val commentsCount: Int,
    val fileUrls: List<String>,
) {
    companion object {
        fun from(post: Post, postFiles: List<PostFile>, commentsCount: Int): PostView {
            val fileUrls = postFiles.map { it.url }

            return PostView(
                id = post.id!!,
                nickname = post.member.nickname,
                isAnonymous = post.isAnonymous,
                createdAt = post.createdAt,
                title = post.title,
                content = post.content,
                commentsCount = commentsCount,
                fileUrls = fileUrls
            )
        }
    }
}