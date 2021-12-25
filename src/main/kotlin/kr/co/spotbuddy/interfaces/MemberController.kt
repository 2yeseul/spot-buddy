package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.MemberService
import kr.co.spotbuddy.interfaces.request.SignUpForm
import kr.co.spotbuddy.interfaces.response.SignUpResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/")
    fun register(@RequestBody signUpForm: SignUpForm): ResponseEntity<SignUpResponse> {
        val response = memberService.signUp(signUpForm)

        return ResponseEntity.accepted().body(response)
    }
}