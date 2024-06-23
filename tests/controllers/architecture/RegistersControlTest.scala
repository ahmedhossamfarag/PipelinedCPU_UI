package controllers.architecture

import models.architecture.Registers
import org.scalatest.funsuite.AnyFunSuiteLike

class RegistersControlTest extends AnyFunSuiteLike {

  test("testIndexOf") {
    val registers = new RegistersControl(new Registers())
    assert(registers.indexOf(100).isEmpty)
    assert(registers.indexOf(2).contains(1))
  }

  test("testGet") {
    val registers = new RegistersControl(new Registers())
    registers.set(2, 12)
    assert(registers.get(2).contains(12))
    assert(registers.get(100).isEmpty)
  }
}
