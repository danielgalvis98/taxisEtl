package com.taxis.etl.extract.s3

import com.taxis.etl.extract.IDownload
import software.amazon.awssdk.core.ResponseInputStream
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.{GetObjectRequest, GetObjectResponse}

import java.io.File
import java.nio.file.{Files, Path, StandardCopyOption}

class DownloadS3Object(s3Client: S3Client, bucketName: String, folder: Path) extends IDownload {
    def perform(filename: String): Unit = {
        val getS3Objects = GetObjectRequest
            .builder()
            .bucket(bucketName)
            .key(filename)
            .build()
        val initialStream: ResponseInputStream[GetObjectResponse] = s3Client.getObject(getS3Objects)
        val targetFile = new File(folder.toFile, filename)
        Files.copy(initialStream, targetFile.toPath, StandardCopyOption.REPLACE_EXISTING)
    }

    def perform(filenames: Array[String]): Unit = {
        filenames.foreach((filename: String) => {
            System.out.println("Downloading " + filename + " from s3 bucket " + bucketName)
            perform(filename)
        })
    }
}
