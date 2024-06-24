package controllers.architecture

import base.Runner
import models.architecture.Codes.Register
import org.scalatest.funsuite.AnyFunSuiteLike

class ForwardControlTest extends AnyFunSuiteLike {

  test("test1") {
    val a = "addi ax, ax, 10"
    val b = "add bx, bx, ax"
    for(i <- 0 to 4){
      val inst: Array[String] = Array(a).concat(Array.fill(i)("noop")).appended(b).concat(Array.fill(4)("noop"))
      val ac = base.Builder.build(inst)
      val runner = new Runner(ac)
      runner.proceed()
      assert(ac.registersControl.get(Register.BX.code).contains(10))
    }
  }

  test("test2") {
    val a = "lw ax, 0(ds)"
    val b = "add bx, bx, ax"
    val data = Array("11", "9")
    for (i <- 0 to 4) {
      val inst: Array[String] = Array(a).concat(Array.fill(i)("noop")).appended(b).concat(Array.fill(4)("noop"))
      val ac = base.Builder.build(inst, Some(data))
      val runner = new Runner(ac)
      runner.proceed()
      assert(ac.registersControl.get(Register.BX.code).contains(11))
    }
  }

  test("test3") {
    val a = "lw ax, 0(ds)"
    val b = "sw ax, 1(ds)"
    val data = Array("11", "9")
    for (i <- 0 to 4) {
      val inst: Array[String] = Array(a).concat(Array.fill(i)("noop")).appended(b).concat(Array.fill(4)("noop"))
      val ac = base.Builder.build(inst, Some(data))
      val runner = new Runner(ac)
      runner.proceed()
      assert(ac.arch.memory.get(1) == 11)
    }
  }
}
