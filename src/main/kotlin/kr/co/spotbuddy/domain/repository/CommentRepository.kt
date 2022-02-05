package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByPostId(postId: Long): List<Comment>
}