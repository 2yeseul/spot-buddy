package kr.co.spotbuddy.domain

import org.springframework.data.jpa.repository.JpaRepository

interface DeleteAccountRepository: JpaRepository<DeleteAccount, Long> {
    fun findByEmail(email: String): DeleteAccount?
}