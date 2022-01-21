package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Block
import kr.co.spotbuddy.domain.repository.BlockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlockService(
    private val blockRepository: BlockRepository,
    private val memberService: MemberService,
) {
    infix fun Long.isAlreadyBlockedBy(blockExecutor: Long): Boolean {
        return blockRepository.existsByBlockExecutorAndGetBlocked(blockExecutor, this)
    }

    @Transactional
    fun cancelBlock(blockExecutor: Long, getBlockedNickname: String) {
        val getBlocked = memberService.getByNickname(getBlockedNickname).id

        if (getBlocked!! isAlreadyBlockedBy blockExecutor ) {
            blockRepository.deleteByBlockExecutorAndGetBlocked(blockExecutor, getBlocked)
        }
    }

    fun getMemberBlocks(memberId: Long): List<BlockResponse> {
        return blockRepository.findAllByBlockExecutor(memberId)
    }

    @Transactional
    fun block(blockExecutor: Long, getBlocked: Long) {
        if (getBlocked isAlreadyBlockedBy blockExecutor) {
            return
        }
        blockRepository.save(Block.of(blockExecutor, getBlocked))
    }

}

typealias BlockResponse = Block