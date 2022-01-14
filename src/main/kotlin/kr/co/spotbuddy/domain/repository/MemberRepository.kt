package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findAllByTeamIndex(index: Int): List<Member>
    fun findByNickname(nickname: String): Member?
    fun findByEmail(email: String): Member?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
    fun countByTeamIndex(teamIndex: Int): Int
}