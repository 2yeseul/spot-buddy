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
}