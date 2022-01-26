package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.Tour
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import java.awt.print.Pageable

interface TourRepository: JpaRepository<Tour, Long> {
    fun existsByIsTempSavedAndMember(isTempSaved: Boolean, member: Member): Boolean
    fun findFirstByIsTempSavedAndMemberOrderByIdDesc(isTempSaved: Boolean, member: Member): Tour
    fun findAllByIsTempSavedAndMember(isTempSaved: Boolean, member: Member): List<Tour>
}