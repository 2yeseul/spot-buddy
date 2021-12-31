package kr.co.spotbuddy.domain

data class TourLookUpEvent(
    val tour: Tour
) {
    companion object {
        fun fire(tour: Tour): TourLookUpEvent {
            return TourLookUpEvent(tour)
        }
    }
}