package kr.co.spotbuddy.interfaces.request

import org.springframework.web.multipart.MultipartFile

data class PostRequest(
    val title: String,
    val content: String,
    val isAnonymous: Boolean,
    val files: List<MultipartFile>?,
    val category: Int,
    val teamIndex: Long,
)