package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Member
import kr.co.spotbuddy.domain.MemberRepository
import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import kr.co.spotbuddy.interfaces.request.SignUpForm
import kr.co.spotbuddy.interfaces.response.CommonView
import kr.co.spotbuddy.util.BadWordsUtil
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class MemberService(
    private val memberRepository: MemberRepository,
    private val deleteAccountService: DeleteAccountService,
) {
    @Transactional
    fun signUp(signUpForm: SignUpForm): Member {
        return memberRepository.save(Member.from(signUpForm))
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
}