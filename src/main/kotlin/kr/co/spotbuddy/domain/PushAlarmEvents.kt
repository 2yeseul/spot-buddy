package kr.co.spotbuddy.domain

import kr.co.spotbuddy.interfaces.request.PushAlarmTarget

data class PushAlarmEvents(
    val target: PushAlarmTarget
) {
    companion object {
        fun fire(target: PushAlarmTarget): PushAlarmEvents {
            return PushAlarmEvents(target)
        }
    }
}