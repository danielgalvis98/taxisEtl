package com.taxis.etl.extract

import sys.process._
import java.net.URL
import java.io.File
import java.nio.file.Path
import scala.language.postfixOps
class URLDownload(baseUrl: String, folder: Path) extends IDownload {
  def perform(filenames: Array[String]): Unit = {
    filenames.foreach((filename: String) => {
      System.out.println("Downloading " + baseUrl + File.separator + filename)
      new URL(baseUrl + "/" + filename) #> new File(folder.toFile, filename) !!
    })
  }
}
