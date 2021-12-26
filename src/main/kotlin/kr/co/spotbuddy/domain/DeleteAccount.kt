package kr.co.spotbuddy.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class DeleteAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    val email: String,
    val reasonIndex: String,
    val etc: String?,
    val deleteTime: LocalDateTime,
) {
}