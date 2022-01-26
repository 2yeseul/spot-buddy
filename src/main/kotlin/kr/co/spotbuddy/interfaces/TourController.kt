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
    // TODO : 테마 검색, 홈 검색
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
        val tours = tourService.getTours(memberId, pageable, sortType)
        return ResponseEntity.ok(tours.map { TourView.of(it, null) })
    }

    @GetMapping("/")
    fun getMemberTours(memberId: Long, pageable: Pageable): ResponseEntity<List<TourView>> {
        val tours = tourService.getMemberTours(memberId, pageable)

        return ResponseEntity.ok(tours.map { TourView.of(it, null) })
    }

    @PatchMapping("/{id}")
    fun modifyTour(memberId: Long, @PathVariable id: Long, @RequestBody tourRequest: TourRequest): ResponseEntity<HttpStatus> {
        tourService.modifyTour(tourRequest, memberId)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/{id}")
    fun deleteTour(memberId: Long, @PathVariable id: Long): ResponseEntity<HttpStatus> {
        tourService.deleteById(memberId, id)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }

    @PutMapping("/close/{id}")
    fun closeTour(memberId: Long, @PathVariable id: Long): ResponseEntity<HttpStatus> {
        tourService.closeTour(memberId, id)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }
}