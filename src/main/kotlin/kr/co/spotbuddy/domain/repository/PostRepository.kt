package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long> {
}