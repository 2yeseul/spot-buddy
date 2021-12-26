package kr.co.spotbuddy.domain

import javax.persistence.*

@Entity
class TourTheme(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    var theme: String,

    val tourId: Long
) {
}