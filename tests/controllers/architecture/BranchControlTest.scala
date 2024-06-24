package controllers.architecture

import org.scalatest.funsuite.AnyFunSuiteLike
import models.architecture.Architecture
import models.architecture.Codes.OpCode

class BranchControlTest extends AnyFunSuiteLike {

  test("testWill") {
    val arch = new Architecture(Array.empty, Array.empty)
    val branchControl = new BranchControl(arch)

    arch.ex_mem.aluZero = true
    arch.ex_mem.opCode = OpCode.BEQ.code
    branchControl.updateSignals()
    assert(arch.branchSignals.willBranch)

    arch.ex_mem.aluZero = false
    arch.ex_mem.opCode = OpCode.BEQ.code
    branchControl.updateSignals()
    assert(!arch.branchSignals.willBranch)

    arch.ex_mem.aluZero = false
    arch.ex_mem.opCode = OpCode.BNE.code
    branchControl.updateSignals()
    assert(arch.branchSignals.willBranch)

    arch.ex_mem.aluZero = false
    arch.ex_mem.opCode = OpCode.J.code
    branchControl.updateSignals()
    assert(!arch.branchSignals.willBranch)

    arch.ex_mem.aluZero = true
    arch.ex_mem.opCode = OpCode.ALU.code
    branchControl.updateSignals()
    assert(!arch.branchSignals.willBranch)
  }

  test("willJump"){
    val arch = new Architecture(Array.empty, Array.empty)
    val branchControl = new BranchControl(arch)
    arch.if_id.inst.setWith(opCode = OpCode.J.code)
    branchControl.updateSignals()
    assert(arch.branchSignals.willJump)
  }

}
