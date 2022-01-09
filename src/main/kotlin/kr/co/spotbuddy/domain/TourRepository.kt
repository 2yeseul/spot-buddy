package kr.co.spotbuddy.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TourRepository: JpaRepository<Tour, Long> {
    fun existsByIsTempSavedAndMember(isTempSaved: Boolean, member: Member): Boolean
    fun findFirstByIsTempSavedAndMemberOrderByIdDesc(isTempSaved: Boolean, member: Member): Tour
    fun findAllByIsTempSavedAndMember(isTempSaved: Boolean, member: Member): List<Tour>
}