package kr.co.spotbuddy.application

interface EmailService {
    fun sendMail(to: String, subject: String, message: String)
}