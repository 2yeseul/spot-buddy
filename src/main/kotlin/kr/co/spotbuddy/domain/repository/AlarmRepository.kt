package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Alarm
import kr.co.spotbuddy.domain.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface AlarmRepository: JpaRepository<Alarm, Long> {
    @Transactional(readOnly = true)
    fun findAllByMemberOrderByIdDesc(member: Member, pageable: Pageable): Page<Alarm>
    fun existsByMember(member: Member): Boolean
    fun existsByMemberAndIsRead(member: Member, isRead: Boolean): Boolean
    fun deleteAllByMember(member: Member): Unit
}