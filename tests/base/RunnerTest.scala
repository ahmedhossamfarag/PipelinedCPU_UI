package base

import models.architecture.Codes.Register
import org.scalatest.funsuite.AnyFunSuiteLike

class RunnerTest extends AnyFunSuiteLike {

  test("exampleTest"){
    val instFile = "assets/example.asm"
    val ac = base.Builder.buildFiles(instFile)
    val runner = new Runner(ac)
    runner.proceed()
    assert(ac.registersControl.get(Register.AX.code).contains(112))
    assert(ac.registersControl.get(Register.BX.code).contains(12))
    assert(ac.registersControl.get(Register.CX.code).contains(0))
    assert(ac.registersControl.get(Register.DX.code).contains(0))
    assert(ac.registersControl.get(Register.SP.code).contains(12))
    assert(ac.arch.memory.get(3) == 12)
  }

}
