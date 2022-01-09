package kr.co.spotbuddy.interfaces.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.co.spotbuddy.domain.Tour
import java.time.LocalDate
import java.time.LocalDateTime

data class TourView (
    val id: Long?,
    val nickname: String,
    val tourLocation: String,
    val teamIndex: Int,
    val tourTeam: String,
    val nowNumberOfMember: Int,
    val requiredGender: Int?,
    val minimumAge: Int?,
    val maximumAge: Int?,
    val tourTitle: String,
    val tourContent: String,
    val weather: Int,
    val scrapCount: Int?,
    val viewCount: Int,
    val age: Int,
    val gender: Int,
    val tourDateDetail: String?,
    val bio: String,
    val tourThemes: List<String>?,
    val minimumMember: Int,
    val maximumMember: Int,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val tourStartDate: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val tourEndDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val createdAt: LocalDateTime,

    val isTempSaved: Boolean,
    val isEnded: Boolean,
) {
    companion object {
        fun of(tour: Tour, tourThemes: List<String>?): TourView {
            return TourView(
                id = tour.id,
                age = (LocalDate.now().dayOfYear) - Integer.parseInt(tour.member.birth) + 1,
                gender = tour.member.gender,
                nickname = tour.member.nickname,
                weather = tour.member.weather,
                tourLocation =  tour.tourLocation,
                teamIndex = tour.member.teamIndex,
                tourTeam = tour.tourTeam,
                tourThemes = tourThemes,
                tourStartDate = tour.startDate,
                tourEndDate = tour.endDate,
                tourTitle = tour.tourTitle,
                createdAt = tour.createdAt,
                tourContent = tour.tourContent,
                requiredGender = tour.requiredGender,
                minimumAge = tour.minimumAge,
                maximumAge = tour.maximumAge,
                nowNumberOfMember = tour.nowMember,
                viewCount = 0,
                tourDateDetail = tour.tourDateDetail,
                bio = tour.bio,
                minimumMember = tour.minimumMember,
                maximumMember = tour.maximumMember,
                isTempSaved = tour.isTempSaved,
                isEnded = false,
                scrapCount = null, // FIXME: scrap 구현 뒤 채우기
            )
        }
    }
}