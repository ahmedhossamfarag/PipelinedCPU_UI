package models.architecture

import org.scalatest.funsuite.AnyFunSuiteLike

class InstructionTest extends AnyFunSuiteLike {

  test("testBOffset") {
    val inst = new Instruction()
    inst.setWith(bOffset = -1)
    assert(inst.bOffset == -1)
  }

  test("testImmediate") {
    val inst = new Instruction()
    inst.setWith(immediate = -1)
    assert(inst.immediate == -1)
  }

}
