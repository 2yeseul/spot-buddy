package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.MemberService
import kr.co.spotbuddy.interfaces.request.SignUpForm
import kr.co.spotbuddy.interfaces.response.SignUpView
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Slf4j
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/")
    fun register(@RequestBody signUpForm: SignUpForm): ResponseEntity<SignUpView> {
        val member = memberService.signUp(signUpForm)

        return ResponseEntity
            .accepted()
            .body(SignUpView(token = member.emailCheckToken, email = member.email))
    }

    @GetMapping("/valid")
    fun checkNicknameOrEmail(@RequestParam nickname: String?, @RequestParam email: String?): ResponseEntity<Boolean> {
        if (nickname == null && email == null) {
            return ResponseEntity
                .badRequest()
                .body(false)
        }
        val result = memberService.checkValidEmailOrEmail(nickname, email)

        return ResponseEntity
            .ok()
            .body(result)
    }
}