package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.TourService
import kr.co.spotbuddy.interfaces.request.TourRequest
import kr.co.spotbuddy.interfaces.request.TourSortType
import kr.co.spotbuddy.interfaces.response.TourView
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tours")
class TourController(
    private val tourService: TourService
) {
    // TODO : 테마 검색, 홈 검색, 임시 저장 조회,
    @DeleteMapping("/temp")
    fun deleteTemporaryTour(memberId: Long): ResponseEntity<HttpStatus> {
        tourService.deleteAllTemporarySavedTours(memberId)

        return ResponseEntity
            .accepted()
            .body(HttpStatus.ACCEPTED)
    }

    @PostMapping("/")
    fun uploadTour(@RequestBody tourRequest: TourRequest, memberId: Long): ResponseEntity<TourView> {
        val tourAndThemes = tourService.saveTour(tourRequest, memberId)

        return ResponseEntity
            .ok()
            .body(TourView.of(tourAndThemes.first, tourAndThemes.second))
    }

    @GetMapping("/{id}")
    fun getTour(@PathVariable id: Long): ResponseEntity<TourView> {
        val tour = tourService.getTourById(id)

        return ResponseEntity
            .ok()
            .body(TourView.of(tour, null))
    }

    @GetMapping("/")
    fun getTours(memberId: Long?, pageable: Pageable, sortType: TourSortType): ResponseEntity<List<TourView>> {
        return if (memberId != null) {
            val tours = tourService.getFilteredTours(memberId, pageable, sortType)
                .map { TourView.of(it, null) }

            ResponseEntity.ok(tours)
        } else ResponseEntity.ok(listOf())
    }
}