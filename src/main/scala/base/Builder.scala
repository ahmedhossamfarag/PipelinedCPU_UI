package base

import controllers.architecture.ArchitectureController
import models.architecture.Architecture
import scala.io.Source

object Builder {
  def build(instructions: Array[String], data: Option[Array[String]] = None): ArchitectureController = {
    if(instructions.isEmpty) throw new Exception("Empty Instructions")
    if(data.isDefined && data.get.isEmpty) throw new Exception("Empty Data")

    val encodedData = data.map(Encoder.encodeData).getOrElse(Array.fill(100)(0))

    new ArchitectureController(new Architecture(Encoder.encode(instructions), encodedData))
  }

  def buildFiles(instructionsFile: String, dataFile: Option[String] = None): ArchitectureController =
    build(readFile(instructionsFile), dataFile.map(readFile))

  private def readFile(filename: String): Array[String] = {
    val source = Source.fromFile(filename)
    try {
      source.getLines().filter(!_.isBlank).toArray
    } finally {
      source.close()
    }
  }
}
