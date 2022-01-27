package kr.co.spotbuddy.interfaces.response

import com.fasterxml.jackson.annotation.JsonFormat
import kr.co.spotbuddy.domain.Alarm
import kr.co.spotbuddy.domain.AlarmType
import kr.co.spotbuddy.domain.Member
import java.time.LocalDateTime

data class AlarmView(
    val alarmId: Long,
    val alarmType: AlarmType,
    val derivedId: Long,
    val isRead: Boolean,
    val title: String,
    val body: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd EEE HH:mm", locale = "ko")
    val createdAt: LocalDateTime,

    val teamIndex: Int,
) {
    companion object {
        fun of(alarm: Alarm, member: Member): AlarmView {
            return AlarmView(
                alarmId = alarm.id!!,
                alarmType = alarm.alarmType,
                derivedId = alarm.derivedId,
                isRead = alarm.isRead,
                title = alarm.title,
                body = alarm.body,
                createdAt = alarm.createdAt,
                teamIndex = member.teamIndex,
            )
        }
    }
}
