package kr.co.spotbuddy.application

import kr.co.spotbuddy.exception.CustomException
import kr.co.spotbuddy.exception.ExceptionDefinition
import lombok.Getter
import lombok.Setter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class S3UploadComponent(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String
) {
    fun getBucket(): String {
        return bucket
    }
}