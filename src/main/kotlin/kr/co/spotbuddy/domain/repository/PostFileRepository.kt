package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.PostFile
import org.springframework.data.jpa.repository.JpaRepository

interface PostFileRepository: JpaRepository<PostFile, Long> {
}