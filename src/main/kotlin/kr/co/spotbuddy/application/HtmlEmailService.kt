package kr.co.spotbuddy.application

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import javax.mail.MessagingException
import kotlin.math.log

@Component
@Slf4j
class HtmlEmailService(
    private val javaMailSender: JavaMailSender
): EmailService {
    private val log: Logger = LoggerFactory.getLogger(HtmlEmailService::class.java)

    override fun sendMail(to: String, subject: String, message: String) {
        val mimeMessage = javaMailSender.createMimeMessage()

        try {
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, false, "UTF-8")
            mimeMessageHelper.setTo(to)
            mimeMessageHelper.setSubject(subject)
            mimeMessageHelper.setText(message)

            javaMailSender.send(mimeMessage)
        } catch (e: MessagingException) {
            log.error("$to : failed to send email", e)
        }
    }
}