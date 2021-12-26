package kr.co.spotbuddy.domain

import kr.co.spotbuddy.interfaces.request.SignUpForm
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@DynamicUpdate
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    val email: String,
    var isEmailVerified: Boolean,
    var emailCheckToken: String,
    val emailCheckTokenGeneratedAt: LocalDateTime?,
    var nickname: String,
    val name: String,
    val birth: String,
    var password: String,
    var teamIndex: Int,
    val isAgeOlderThan14: Boolean,
    val isAgreeOnTOS: Boolean,
    var isAgreeOnGetPromotion: Boolean,
    val gender: Int,
    var weather: Int = 50,
    var isDeleted: Boolean?
) {
    fun removeMember() {
        this.isDeleted = true
    }

    fun updateWeather(score: Int) {
        this.weather += score
    }

    fun verifyEmail() {
        this.isEmailVerified = true
    }

    fun updateEmailCheckToken(token: String) {
        this.emailCheckToken = token
    }

    fun verifyAccount() {
        this.isEmailVerified = true
    }

    companion object {
        fun from(signUpForm: SignUpForm, encodedPassword: String): Member {
            return Member(
                id = null,
                email = signUpForm.email,
                nickname = signUpForm.nickname,
                name = signUpForm.name,
                birth = signUpForm.birth,
                password = encodedPassword,
                teamIndex = signUpForm.teamIndex,
                gender = signUpForm.gender,
                weather = 50,
                isAgreeOnGetPromotion = signUpForm.isAgreeOnGetPromotion,
                emailCheckToken = UUID.randomUUID().toString(),
                emailCheckTokenGeneratedAt = LocalDateTime.now(),
                isAgeOlderThan14 = true,
                isAgreeOnTOS = true,
                isDeleted = false,
                isEmailVerified = false,
            )
        }
    }
}