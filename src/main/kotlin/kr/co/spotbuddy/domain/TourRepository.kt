package kr.co.spotbuddy.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TourRepository: JpaRepository<Tour, Long> {
}