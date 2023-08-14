package com.taxis.etl.extract

import java.io.File
import java.net.URL
import java.nio.file.Path
import scala.language.postfixOps
import scala.sys.process._

class NoDownload() extends IDownload {
  def perform(filenames: Array[String]): Unit = {
    System.out.println("Source is local files, no download is performed")
  }
}
