package kr.co.spotbuddy.interfaces.request

data class MemberRequest(
    val email: String,
    val nickname: String,
    val name: String,
    val birth: String,
    val password: String,
    val gender: Int,
    val teamIndex: Int,
    val isAgreeOnGetPromotion: Boolean
)
