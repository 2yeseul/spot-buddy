package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.TourTheme
import kr.co.spotbuddy.domain.repository.TourThemeRepository
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

    @Transactional
    fun modifyThemes(themes: List<String>, tourId: Long) {
        val previousTourThemes = tourThemeRepository.findAllByTourId(tourId)

        previousTourThemes?.let {
            tourThemeRepository.deleteAllInBatch(it)
        }

        val newTourThemes = themes.map {
            TourTheme.of(it, tourId)
        }

        tourThemeRepository.saveAll(newTourThemes)
    }
}