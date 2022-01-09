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


data class TourThemeEvent(
    val themes: List<String>,
    val tourId: Long
) {
    companion object {
        fun fire(themes: List<String>, tourId: Long): TourThemeEvent {
            return TourThemeEvent(themes, tourId)
        }
    }
}