package kr.co.spotbuddy.interfaces.request

import com.fasterxml.jackson.annotation.JsonFormat
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