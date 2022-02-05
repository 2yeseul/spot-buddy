package kr.co.spotbuddy.interfaces

import kr.co.spotbuddy.application.CommentService
import kr.co.spotbuddy.interfaces.request.CommentRequest
import kr.co.spotbuddy.interfaces.response.CommentView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/")
    fun saveComment(memberId: Long, @RequestBody commentRequest: CommentRequest): ResponseEntity<HttpStatus> {
        commentService.saveComment(memberId, commentRequest)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping("/")
    suspend fun getCommentsByPostId(postId: Long): ResponseEntity<List<CommentView>> {
        val (comments, reactionsMap, post) = commentService.getAllCommentsByPostId(postId)

        val commentsView = comments.map {
            CommentView.from(post, it, reactionsMap[it.id!!] ?: emptyList())
        }

        return ResponseEntity(commentsView, HttpStatus.OK)
    }
}