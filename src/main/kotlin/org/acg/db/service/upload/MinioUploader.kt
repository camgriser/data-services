package org.acg.db.service.upload

import io.minio.*
import io.minio.errors.MinioException
import org.acg.db.context.config.MinioConfig

class MinioUploader (
    private val config: MinioConfig,
    private val minioClient: MinioClient
) {

    fun upload(filePath: String, fileName: String): String {
        val currentTime = System.currentTimeMillis()
        try {
            // Make Bucket if not exists
            val found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(config.bucket).build())

            if (!found)
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(config.bucket).build())

            // Upload Object
            minioClient.uploadObject(
                UploadObjectArgs.builder()
                    .bucket(config.bucket)
                    .`object`("$currentTime/$fileName")
                    .filename("$filePath/$fileName")
                    .build()
            )
        } catch (e: MinioException) {
            e.printStackTrace()
            return ""
        }
        return "$currentTime/$fileName"
    }

    fun getImportUrl(objPath: String): String =
        "s3://import-utility/$objPath?AWS_ENDPOINT=${config.url}&AWS_ACCESS_KEY_ID=${config.accessKey}&AWS_SECRET_ACCESS_KEY=${config.secretKey}"

    fun baseUrl(): String = "s3://${config.url}/${config.bucket}"

    fun delete(filePath: String) =
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(config.bucket).`object`(filePath).build())
}