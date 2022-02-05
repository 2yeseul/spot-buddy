package kr.co.spotbuddy.domain

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
data class PostFile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val post: Post,

    val url: String,
) {
    companion object {
        fun of(post: Post, url: String): PostFile {
            return PostFile(null, post, url)
        }
    }
}
