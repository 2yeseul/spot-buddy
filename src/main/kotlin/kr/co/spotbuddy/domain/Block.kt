package kr.co.spotbuddy.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Block(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    val blockExecutor: Long,

    val getBlocked: Long,
) {
    companion object {
        fun of(blockExecutor: Long, getBlocked: Long): Block {
            return Block(id = null, blockExecutor, getBlocked)
        }
    }
}