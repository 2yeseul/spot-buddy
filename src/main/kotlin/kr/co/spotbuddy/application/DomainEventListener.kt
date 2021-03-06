package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.SendEmailEvent
import kr.co.spotbuddy.domain.TourLookUpEvent
import kr.co.spotbuddy.domain.TourThemeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class DomainEventListener(
    private val memberService: MemberService,
    private val emailService: EmailService,
    private val templateEngine: TemplateEngine,
    private val tourThemeService: TourThemeService,
) {
    @EventListener
    fun handle(event: SendEmailEvent) {
        val token = event.member.emailCheckToken
        val email = event.member.email

        val context = Context()

        context.setVariable("link", "/check-email-token?token=$token&email=$email")
        context.setVariable("host", "https://www.spotbuddy.co.kr/api")

        val message = templateEngine.process("confirm-email", context)

        emailService.sendMail(to = email, subject = "SPOT BUDDY 가입 인증 메일입니다. (1시간 유효)", message = message)
    }

    @EventListener
    fun handle(event: TourLookUpEvent) {
        val tour = event.tour
        tour.apply {
            this.viewCount += 1
        }
    }

    @TransactionalEventListener
    fun handle(event: TourThemeEvent) {
        tourThemeService.saveTourThemes(event.themes, event.tourId)
    }
}