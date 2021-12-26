package kr.co.spotbuddy.domain

import org.springframework.context.ApplicationEvent

data class MemberSignUpEvent(
    val member: Member
): ApplicationEvent(member) {
    companion object {
        fun fire(member: Member): MemberSignUpEvent {
            return MemberSignUpEvent(member)
        }
    }
}