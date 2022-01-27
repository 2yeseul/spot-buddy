package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.BlockResponse
import kr.co.spotbuddy.application.BlockService
import kr.co.spotbuddy.interfaces.request.BlockRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/block")
class BlockController(
    private val blockService: BlockService,
) {
    @DeleteMapping("/")
    fun cancelBlock(@RequestParam blockExecutor: Long, @RequestParam getBlockedNickname: String): ResponseEntity<HttpStatus> {
        blockService.cancelBlock(blockExecutor, getBlockedNickname)

        return ResponseEntity
            .accepted()
            .body(HttpStatus.ACCEPTED)
    }

    @GetMapping("/")
    fun getMemberBlocks(@RequestParam memberId: Long): ResponseEntity<List<BlockResponse>> {
        val blocks = blockService.getMemberBlocks(memberId)

        return ResponseEntity.ok(blocks)
    }

    @PostMapping("/")
    fun doBlockMember(@RequestBody blockRequest: BlockRequest): ResponseEntity<HttpStatus> {
        blockService.doBlock(blockExecutor = blockRequest.blockExecutor, getBlocked = blockRequest.getBlocked)

        return ResponseEntity.ok(HttpStatus.ACCEPTED)
    }
}