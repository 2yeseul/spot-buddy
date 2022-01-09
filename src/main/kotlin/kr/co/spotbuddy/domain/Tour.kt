package kr.co.spotbuddy.domain

import com.fasterxml.jackson.annotation.JsonFormat
import kr.co.spotbuddy.interfaces.request.TourRequest
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicUpdate
class Tour(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,

    var tourLocation: String,
    var tourTeam: String,

    @JsonFormat(pattern = "yyyy-MM-dd")
    var startDate: LocalDate,

    @JsonFormat(pattern = "yyyy-MM-dd")
    var endDate: LocalDate,

    var tourTitle: String,
    var tourContent: String,

    var requiredGender: Int?,
    var minimumAge: Int?,
    var maximumAge: Int?,

    val createdAt: LocalDateTime,

    var nowMember: Int,
    var minimumMember: Int,
    var maximumMember: Int,

    var viewCount: Int,
    var isEnded: Boolean,

    var tourDateDetail: String,
    var bio: String,

    var isTempSaved: Boolean,
) {
    fun update(tourRequest: TourRequest) {
        this.tourLocation = tourRequest.tourLocation
        this.tourTeam = tourRequest.tourTeam
        this.tourTitle = tourRequest.tourTitle
        this.tourContent = tourRequest.tourContent
        this.requiredGender = tourRequest.requiredGender
        this.minimumAge = tourRequest.minimumAge
        this.maximumAge = tourRequest.maximumAge
        this.tourDateDetail = tourRequest.tourDateDetail
        this.bio = tourRequest.bio
        this.startDate = tourRequest.tourStartDate
        this.endDate = tourRequest.tourEndDate
        this.isTempSaved = tourRequest.isTempSaved
        this.isEnded = tourRequest.isEnded
    }

    companion object {
        fun of(tourRequest: TourRequest, member: Member): Tour {
            return Tour(
                id = tourRequest.id,
                member = member,
                tourLocation =  tourRequest.tourLocation,
                tourTeam = tourRequest.tourTeam,
                startDate = tourRequest.tourStartDate,
                endDate = tourRequest.tourEndDate,
                tourTitle = tourRequest.tourTitle,
                tourContent = tourRequest.tourContent,
                requiredGender = tourRequest.requiredGender,
                minimumAge = tourRequest.minimumAge,
                maximumAge = tourRequest.maximumAge,
                createdAt = LocalDateTime.now(),
                nowMember = 0,
                viewCount = 0,
                tourDateDetail = tourRequest.tourDateDetail,
                bio = tourRequest.bio,
                minimumMember = tourRequest.minimumMember,
                maximumMember = tourRequest.maximumMember,
                isTempSaved = tourRequest.isTempSaved,
                isEnded = false,
            )
        }
    }
}