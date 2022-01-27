package kr.co.spotbuddy.interfaces.request

data class BlockRequest(
    val blockExecutor: Long,
    val getBlocked: Long,
)
