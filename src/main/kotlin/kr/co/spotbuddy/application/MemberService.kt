package kr.co.spotbuddy.application

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.MemberRepository
import kr.co.spotbuddy.domain.SendEmailEvent
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.SignUpForm
import kr.co.spotbuddy.util.BadWordsUtil
import lombok.extern.slf4j.Slf4j
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

@Service
@Slf4j
class MemberService(
    private val memberRepository: MemberRepository,
    private val deleteAccountService: DeleteAccountService,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun signUp(signUpForm: SignUpForm): Member {
        val member = memberRepository.save(Member.from(signUpForm, passwordEncoder.encode(signUpForm.password)))

        CoroutineScope(Dispatchers.IO).launch {
            applicationEventPublisher.publishEvent(SendEmailEvent.fire(member))
        }

        return member
    }

    fun getByEmail(email: String): Member {
        return memberRepository.findByEmail(email) ?: throw CustomException(ExceptionDefinition.NOT_FOUND_USER)
    }

    @Transactional
    fun resendConfirmEmail(email: String) {
        val member = getByEmail(email)
        member.updateEmailCheckToken(UUID.randomUUID().toString())

        applicationEventPublisher.publishEvent(SendEmailEvent.fire(member))
    }

    fun existsByNickname(nickname: String): Boolean {
        return memberRepository.existsByNickname(nickname)
    }

    fun existsByEmail(email: String): Boolean {
        return memberRepository.existsByEmail(email)
    }

    fun checkValidEmailOrEmail(nickname: String?, email: String?): Boolean {
        if (nickname != null) {
            return isValidNickname(nickname)
        }
        return isValidEmail(email!!)
    }

    private fun isValidNickname(nickname: String): Boolean {
        when {
            BadWordsUtil.isBadWordsContains(nickname) -> throw CustomException(ExceptionDefinition.BAD_WORDS_IN_NICKNAME)
            existsByNickname(nickname) -> throw CustomException(ExceptionDefinition.ALREADY_EXISTS_NICKNAME)
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        when {
            existsByEmail(email) -> throw CustomException(ExceptionDefinition.ALREADY_EXISTS_EMAIL)
            deleteAccountService.isDeleteAccountWithin10Days(email) -> throw CustomException(ExceptionDefinition.DELETE_ACCOUNT_WITHIN_10DAYS)
        }

        return true
    }

    fun emailCheck(token: String, email: String, userAgent: String): String {
        val member = getByEmail(email)

        // TODO : IOS Device Token 검사

        val durationTime = Duration.between(member.emailCheckTokenGeneratedAt, LocalDateTime.now()).toHoursPart()

        when {
            member.emailCheckToken != token -> throw CustomException(ExceptionDefinition.INVALID_EMAIL_TOKEN)
            durationTime isBeforeThan 1 -> {
                member.verifyAccount()

                return if (userAgent.contains("iPhone")) {
                    "spot://path/deeplink"
                } else {
                    "https://www.spotbuddy.co.kr/"
                }
            }
        }

        // TODO : Login
        return "https://www.spotbuddy.co.kr/api/check-email/end"
    }

    private infix fun Int.isBeforeThan(hour: Int): Boolean {
        return this < hour
    }
}