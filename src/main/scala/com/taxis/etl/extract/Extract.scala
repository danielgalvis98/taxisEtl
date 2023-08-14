package com.taxis.etl.extract

import com.taxis.etl.extract.s3.DownloadS3Object
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

import java.nio.file.{Files, Path, Paths}

object Extract {
  def getFilenames(): Array[String] = {
    def folder: String = ReadFromEnv.readDownloadFolder()
    ReadFromEnv.readTaxisETLFileName().map((x: String) => folder + "/" + x)
  }

  def getDownloadedFilenames(): Array[String] = {
    performDownload()
    getFilenames()
  }

  def performDownload(): Unit = {
    def folder: String = ReadFromEnv.readDownloadFolder()
    def path = Paths.get(folder)
    def filenames : Array[String] = ReadFromEnv.readTaxisETLFileName()

    if (!(Files.exists(path) && Files.isDirectory(path))) {
      Files.createDirectory(path)
    }
    download(ReadFromEnv.readDownloadSource(), path).perform(filenames)
  }

  def download(source: String, path: Path): IDownload = {
    source match {
      case "s3" => new DownloadS3Object(
        S3Client.builder.region(Region.US_EAST_2).build, ReadFromEnv.readBucketName(), path
      )
      case "url" => new URLDownload(ReadFromEnv.readBaseUrl().get, path)
      case _ => new NoDownload
    }
  }

    def main(args: Array[String]): Unit = {
      performDownload()
    }
}
