package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.repository.TourSearchQueryRepository
import org.springframework.stereotype.Service

@Service
class TourSearchService(
    private val tourSearchQueryRepository: TourSearchQueryRepository,
) {
}