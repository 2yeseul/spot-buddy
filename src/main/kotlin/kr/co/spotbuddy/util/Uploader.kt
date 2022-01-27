package kr.co.spotbuddy.util

import org.springframework.web.multipart.MultipartFile

interface Uploader {
    fun upload(file: MultipartFile, dirName: String): String
}