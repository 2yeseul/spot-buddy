package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.repository.DeleteAccountRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class DeleteAccountService(
    private val deleteAccountRepository: DeleteAccountRepository,
) {
    fun isDeleteAccountWithin10Days(email: String): Boolean {
        val deleteAccount = deleteAccountRepository.findByEmail(email)

        deleteAccount?.let {
            val today = LocalDate.now()
            val period = Period.between(it.deleteTime.toLocalDate(), today)

            if (period.days < 10) {
                return true
            }
        }

        return false
    }
}