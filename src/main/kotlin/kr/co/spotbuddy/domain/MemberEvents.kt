package kr.co.spotbuddy.domain

import org.springframework.context.ApplicationEvent

data class SendEmailEvent(
    val member: Member
): ApplicationEvent(member) {
    companion object {
        fun fire(member: Member): SendEmailEvent {
            return SendEmailEvent(member)
        }
    }
}