package kr.co.spotbuddy.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import kr.co.spotbuddy.application.S3UploadComponent
import kr.co.spotbuddy.exception.CustomException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Component
class S3Uploader(
    private val amazonS3Client: AmazonS3Client,
    private val s3UploadComponent: S3UploadComponent,
) : Uploader {

    private val log: Logger = LoggerFactory.getLogger(S3Uploader::class.java)

    override fun upload(file: MultipartFile, dirName: String): String {
        val convertedFile = file.convert() ?: throw IllegalArgumentException("Converting Multipart to File is failed.")

        return upload(file, dirName)
    }

    private fun upload(file: File, dirName: String): String {
        val fileName = "$dirName/${UUID.randomUUID().toString()}_${file.name}"

        val uploadedImageUrl = putImageToS3(file, fileName)
        removeNewFile(file)

        return uploadedImageUrl
    }

    private fun putImageToS3(file: File, fileName: String): String {
        amazonS3Client.putObject(PutObjectRequest(s3UploadComponent.getBucket(), fileName, file).withCannedAcl(
            CannedAccessControlList.PublicRead))

        return amazonS3Client.getResourceUrl(s3UploadComponent.getBucket(), fileName).toString()
    }

    private fun removeNewFile(targetFile: File) {
        if (targetFile.delete()) {
            log.info("Target File is removed")
        } else {
            log.error("Target File is not removed. ")
        }
    }

    private fun MultipartFile.convert(): File? {
        val convertedFile = File(this.originalFilename!!)

        if(convertedFile.createNewFile()) {
            val fileOutputStream: FileOutputStream = FileOutputStream(convertedFile)
            fileOutputStream.write(this.bytes)

            return convertedFile
        }

        return null
    }
}