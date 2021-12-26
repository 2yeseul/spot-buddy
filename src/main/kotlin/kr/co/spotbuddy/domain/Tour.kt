package kr.co.spotbuddy.domain

import com.fasterxml.jackson.annotation.JsonFormat
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

    var totalMember: Int,
    var nowMember: Int,
    var minimumMember: Int,
    var maximumMember: Int,

    var viewCount: Int,
    var isEnded: Boolean,

    var tourDateDetail: String,
    var bio: String,

    var isTempSaved: Boolean,
) {
}