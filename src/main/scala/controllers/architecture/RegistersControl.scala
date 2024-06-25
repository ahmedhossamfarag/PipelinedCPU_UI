package controllers.architecture

import models.architecture.{Codes, Registers}

class RegistersControl(val registers: Registers) {

  def indexOf(code: Int): Option[Int] = Codes.Register.fromCode(code).map(Codes.Register.indexOf(_).get)

  def get(code: Int): Option[Int] = indexOf(code).map(registers.data(_))

  def set(code: Int, value: Int): Unit = indexOf(code).foreach {i =>
    registers.data(i) = value
    registers.observer.foreach(_.update(i, value))
  }
}
