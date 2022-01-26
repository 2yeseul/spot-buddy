package kr.co.spotbuddy.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class CustomException(exceptionDefinition: ExceptionDefinition) :
    ResponseStatusException(exceptionDefinition.httpStatus, exceptionDefinition.message) {
}

enum class ExceptionDefinition(
    val message: String,
    val httpStatus: HttpStatus,
) {
    BAD_WORDS_IN_NICKNAME("닉네임에 비속어가 포함되어 있습니다.", HttpStatus.NOT_ACCEPTABLE),
    ALREADY_EXISTS_NICKNAME("이미 존재하는 닉네임 입니다.", HttpStatus.NOT_ACCEPTABLE),
    ALREADY_EXISTS_EMAIL("이미 존재하는 이메일 입니다.", HttpStatus.NOT_ACCEPTABLE),
    DELETE_ACCOUNT_WITHIN_10DAYS("10일 이내에 탈퇴한 계정입니다.", HttpStatus.NOT_ACCEPTABLE),
    NOT_FOUND_USER("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND),
    INVALID_EMAIL_TOKEN("유효하지 않은 이메일 토큰 입니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND_TOUR("존재하지 않는 동행입니다.", HttpStatus.NOT_FOUND),
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED)
}