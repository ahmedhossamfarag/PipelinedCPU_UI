package controllers.architecture

import models.architecture.Architecture
import models.architecture.Codes.{ALUCode, OpCode}
import org.scalatest.funsuite.AnyFunSuiteLike

class ALUTest extends AnyFunSuiteLike {

  test("testOutput") {
    val arch = new Architecture(Array.empty, Array.empty)
    val alu = new ALU(new ArchitectureController(arch))

    arch.id_ex.data1 = 11
    arch.id_ex.data2 = 20

    arch.id_ex.inst.setWith(opCode = OpCode.ALU.code,aluCode = ALUCode.ADD.code)
    assert(alu.output == 31)

    arch.id_ex.inst.setWith(opCode = OpCode.ALU.code,aluCode = ALUCode.SUB.code)
    assert(alu.output == -9)

    arch.id_ex.inst.setWith(opCode = OpCode.ALU.code,aluCode = ALUCode.SLT.code)
    assert(alu.output == 1)

    arch.id_ex.inst.setWith(opCode = OpCode.ALU.code,aluCode = ALUCode.SLTU.code)
    assert(alu.output == 1)

    arch.id_ex.inst.setWith(opCode = OpCode.BEQ.code)
    assert(alu.output == -9)

  }

}
