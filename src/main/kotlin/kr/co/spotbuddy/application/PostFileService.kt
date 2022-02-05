package kr.co.spotbuddy.application

import kr.co.spotbuddy.domain.Post
import kr.co.spotbuddy.domain.PostFile
import kr.co.spotbuddy.domain.repository.PostFileRepository
import kr.co.spotbuddy.util.Uploader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class PostFileService(
    private val postFileRepository: PostFileRepository,
    private val uploader: Uploader,
) {
    @Transactional
    fun savePostFiles(post: Post, files: List<MultipartFile>?) {
        val postFiles: MutableList<PostFile> = mutableListOf()

        files?.let { it ->
            it.forEach {
                val url = uploader.upload(it, "static")
                val postFile = PostFile.of(post, url)

                postFiles.add(postFile)
            }
        }

        if (postFiles.isNotEmpty()) {
            postFileRepository.saveAll(postFiles)
        }
    }
}