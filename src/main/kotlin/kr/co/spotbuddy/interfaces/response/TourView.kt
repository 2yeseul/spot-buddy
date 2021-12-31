package kr.co.spotbuddy.interfaces.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class TourView (
    val id: Long?,
    val nickname: String,
    val tourLocation: String,
    val teamIndex: Int,
    val tourTeam: String,
    val nowNumberOfMember: Int,
    val requiredGender: Int,
    val minimumAge: Int,
    val maximumAge: Int,
    val tourTitle: String,
    val tourContent: String,
    val weather: Int,
    val scrapCount: Int,
    val viewCount: Int,
    val age: Int,
    val gender: Int,
    val tourDateDetail: String?,
    val bio: String,
    val tourThemes: List<String>?,
    val minimumMember: Int,
    val maximumMember: Int,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private val tourStartDate: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private val tourEndDate: LocalDate,
)