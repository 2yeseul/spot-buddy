package kr.co.spotbuddy.domain

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
}