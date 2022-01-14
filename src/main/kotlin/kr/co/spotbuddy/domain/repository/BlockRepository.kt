package kr.co.spotbuddy.domain.repository

import kr.co.spotbuddy.domain.Block
import org.springframework.data.jpa.repository.JpaRepository

interface BlockRepository: JpaRepository<Block, Long> {
    fun existsByBlockExecutorAndGetBlocked(blockExecutor: Long, getBlocked: Long): Boolean

    fun existsByBlockExecutor(blockExecutor: Long): Boolean

    fun findAllByBlockExecutor(blockExecutor: Long): List<Block>

    fun findAllByBlockExecutorOrderByIdDesc(blockExecutor: Long): List<Block>

    fun deleteByBlockExecutorAndGetBlocked(blockExecutor: Long, getBlocked: Long)
}