package models.architecture

import models.architecture.Codes.Register

import scala.language.dynamics

class Registers {

  val data: Array[Int] = Array.fill(Register.count)(0)

  override def toString: String =  data.zipWithIndex.map(r => f"${Register.atIndex(r._2).asString} = ${r._1}").mkString(" , ")
}
