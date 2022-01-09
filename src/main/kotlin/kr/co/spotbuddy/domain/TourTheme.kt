package kr.co.spotbuddy.domain

import javax.persistence.*

@Entity
class TourTheme(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    var theme: String,

    val tourId: Long
) {
    fun update(theme: String) {
        this.theme = theme
    }
    companion object {
        fun of(theme: String, tourId: Long): TourTheme {
            return TourTheme(id = null, theme = theme, tourId = tourId)
        }
    }
}