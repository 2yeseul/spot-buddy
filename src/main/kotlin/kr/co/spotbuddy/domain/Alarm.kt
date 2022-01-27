package kr.co.spotbuddy.domain

import kr.co.spotbuddy.interfaces.request.AlarmRequest
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@DynamicUpdate
data class Alarm(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Enumerated(value = EnumType.STRING)
    val alarmType: AlarmType,

    val derivedId: Long, // alarm 송신한 객체

    var isRead: Boolean,

    val title: String,

    val body: String,

    val createdAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    val member: Member,
) {
    fun changeStatusToRead() {
        this.isRead = true
    }

    companion object {
        fun from(alarmRequest: AlarmRequest, member: Member): Alarm {
            return Alarm(id = null,
                alarmType = alarmRequest.alarmType,
                derivedId = alarmRequest.derivedId,
                title = alarmRequest.title,
                body = alarmRequest.body,
                createdAt = LocalDateTime.now(),
                isRead = false,
                member = member,
            )
        }
    }
}

enum class AlarmType(value: String) {
    ALL_NOTICE("전체 공지"),
    TOUR_CONFIRMED("동행 확정"),
    REPLY("커뮤니티 댓글"),
    REPLY_REACTION("커뮤니티 댓글 좋아요"),
}