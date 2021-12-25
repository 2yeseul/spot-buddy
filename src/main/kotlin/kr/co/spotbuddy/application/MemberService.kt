package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.MemberRepository
import kr.co.spotbuddy.interfaces.request.SignUpForm
import kr.co.spotbuddy.interfaces.response.SignUpResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Slf4j
class MemberService(
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun signUp(signUpForm: SignUpForm): SignUpResponse {
        val member = memberRepository.save(Member.from(signUpForm))

        return SignUpResponse(token = member.emailCheckToken, email = member.email)
    }
}