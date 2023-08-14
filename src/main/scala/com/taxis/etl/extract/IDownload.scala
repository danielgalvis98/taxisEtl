package com.taxis.etl.extract

import scala.language.postfixOps
trait IDownload {
    def perform(filenames: Array[String]): Unit
}
