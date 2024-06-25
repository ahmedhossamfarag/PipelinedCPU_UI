package models.architecture

import base.Observer
import models.architecture.Codes.Register

import scala.language.dynamics

class Registers {
  var observer: Option[Observer] = None

  val data: Array[Int] = Array.fill(Register.count)(0)

  override def toString: String =  data.zipWithIndex.map(r => f"${Register.atIndex(r._2).asString} = ${r._1}").mkString(" , ")
}
