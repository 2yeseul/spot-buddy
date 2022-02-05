package kr.co.spotbuddy.domain

import kr.co.spotbuddy.interfaces.request.PostRequest
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicUpdate
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    var category: Int,

    val teamIndex: Long,

    var title: String,
    var content: String,

    var isAnonymous: Boolean,

    val createdAt: LocalDateTime,
    var viewCount: Int,

    var todayViewCount: Int,

    val deletedAt: LocalDateTime?
) {
    companion object {
        fun of(member: Member, postRequest: PostRequest): Post {
            return Post(
                id = null,
                member = member,
                category = postRequest.category,
                teamIndex = postRequest.teamIndex,
                title = postRequest.title,
                content = postRequest.content,
                isAnonymous = postRequest.isAnonymous,
                createdAt = LocalDateTime.now(),
                viewCount = 0,
                todayViewCount = 0,
                deletedAt = null
            )
        }
    }
}