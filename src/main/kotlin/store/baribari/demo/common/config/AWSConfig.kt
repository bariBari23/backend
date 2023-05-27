package store.baribari.demo.common.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSConfig(
    @Value("\${aws.credentials.access-key}")
    private val accessKey: String,
    @Value("\${aws.credentials.secret-key}")
    private val secretKey: String,
    @Value("\${aws.s3.bucket}")
    private val bucketName: String,
) {

    @Bean
    fun amazonS3Client(): AmazonS3? {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)

        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(Regions.AP_NORTHEAST_2)
            .build()
    }
}
