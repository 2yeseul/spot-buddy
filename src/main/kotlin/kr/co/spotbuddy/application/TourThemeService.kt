package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.TourTheme
import kr.co.spotbuddy.domain.TourThemeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TourThemeService(
    private val tourThemeRepository: TourThemeRepository
) {
    @Transactional
    fun saveTourThemes(themes: List<String>, tourId: Long): List<TourTheme> {
        val tourThemes = themes.map { TourTheme.of(it, tourId) }

        return tourThemeRepository.saveAll(tourThemes)
    }
}