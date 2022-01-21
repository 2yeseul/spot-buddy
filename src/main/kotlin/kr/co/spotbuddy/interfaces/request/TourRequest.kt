package kr.co.spotbuddy.interfaces.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import kr.co.spotbuddy.domain.QTour
import java.time.LocalDate

data class TourRequest(
    val id: Long?,
    val tourLocation: String,
    val tourTeam: String,
    val tourTitle: String,
    val tourContent: String,
    val requiredGender: Int?,
    val minimumAge: Int,
    val maximumAge: Int,
    val tourDateDetail: String,
    val bio: String,
    val tourThemes: List<String>?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val tourStartDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val tourEndDate: LocalDate,
    val isTempSaved: Boolean,
    val isEnded: Boolean,
    val minimumMember: Int,
    val maximumMember: Int,
)

enum class TourSortType(val field: OrderSpecifier<*>?) {
    LATEST(null),
    SCRAPS(null),
    MY_GENDER(QTour.tour.requiredGender.desc()),
    MY_AGE(null),
    WEATHER(QTour.tour.member.weather.desc()),
    ENDS_AT(QTour.tour.endDate.desc()),
}