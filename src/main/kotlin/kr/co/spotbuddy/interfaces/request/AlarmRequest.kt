package kr.co.spotbuddy.interfaces.request

import kr.co.spotbuddy.domain.AlarmType

data class AlarmRequest(
    val alarmType: AlarmType,
    val derivedId: Long,
    val title: String,
    val body: String,
    val memberId: Long
)