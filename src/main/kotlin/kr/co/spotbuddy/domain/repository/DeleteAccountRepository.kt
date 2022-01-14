package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.DeleteAccount
import org.springframework.data.jpa.repository.JpaRepository

interface DeleteAccountRepository: JpaRepository<DeleteAccount, Long> {
    fun findByEmail(email: String): DeleteAccount?
}