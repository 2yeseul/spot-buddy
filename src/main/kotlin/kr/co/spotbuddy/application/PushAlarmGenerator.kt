package kr.co.spotbuddy.application

import kr.co.spotbuddy.interfaces.request.PushAlarmTarget
import kr.co.spotbuddy.interfaces.request.PushAlarmType
import org.springframework.stereotype.Component

@Component
class PushAlarmGenerator {
    fun generatePushAlarm(target: PushAlarmTarget) {
        val (to, from, commentReaction, post, type) = target

        when {
            type == PushAlarmType.COMMENT_REACTION -> {
                // TODO: 3/1
            }
        }
    }
}