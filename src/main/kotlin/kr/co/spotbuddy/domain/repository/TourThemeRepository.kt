package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.TourTheme
import org.springframework.data.jpa.repository.JpaRepository

interface TourThemeRepository: JpaRepository<TourTheme, Long> {
    fun findAllByTourId(tourId: Long): List<TourTheme>?
}