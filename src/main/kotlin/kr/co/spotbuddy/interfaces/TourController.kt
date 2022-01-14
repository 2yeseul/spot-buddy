package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.TourService
import kr.co.spotbuddy.interfaces.request.TourRequest
import kr.co.spotbuddy.interfaces.response.TourView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tours")
class TourController(
    private val tourService: TourService
) {
    @DeleteMapping("/temp")
    fun deleteTemporaryTour(@RequestParam memberId: Long): ResponseEntity<HttpStatus> {
        tourService.deleteAllTemporarySavedTours(memberId)

        return ResponseEntity
            .accepted()
            .body(HttpStatus.ACCEPTED)
    }

    @PostMapping("/")
    fun uploadTour(@RequestBody tourRequest: TourRequest, @RequestParam memberId: Long): ResponseEntity<TourView> {
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
    fun getTours(@RequestParam isFiltered: Boolean, @RequestParam memberId: Long, @RequestParam page: Int) {

    }
}