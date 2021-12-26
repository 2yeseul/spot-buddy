package kr.co.spotbuddy.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TourThemeRepository: JpaRepository<TourTheme, Long> {
}