package base

import models.architecture.Codes.{ALUCode, OpCode, Register}
import models.architecture.Instruction
import org.scalatest.funsuite.AnyFunSuiteLike

class EncoderTest extends AnyFunSuiteLike {

  test("testGetOffset") {
    var offset = Encoder.getOffset("12")(0)(Map.empty)
    assert(offset == 12)

    offset = Encoder.getOffset("l12")(0)(Map("l12" -> 5))
    assert(offset == 4)
  }

  test("testEncode") {
    var code = Encoder.encode(Encoder.splitInst("add ax, ax, bx"), 0)(Map.empty)
    val inst = new Instruction()
    inst.setWith(opCode = OpCode.ALU.code, src_1 = Register.AX.code, src_2 = Register.BX.code, dDst = Register.AX.code, aluCode = ALUCode.ADD.code)
    assert(code == inst.get)

    code = Encoder.encode(Encoder.splitInst("addi ax, cx, -1"), 0)(Map.empty)
    inst.setWith(opCode = OpCode.ADDI.code, src_1 = Register.CX.code, immediate = -1, iDst = Register.AX.code)
    assert(code == inst.get)
  }

  test("testSplitInst") {
    var splits = Encoder.splitInst("lw $ax, 2(bx)")
    assert(splits.label.isEmpty)
    assert(splits.opCode == "lw")
    assert(splits.args sameElements Array("$ax", "2(bx)"))

    splits = Encoder.splitInst("L1: lw $Ax, 2(bx)")
    assert(splits.label.contains("l1"))
    assert(splits.opCode == "lw")
    assert(splits.args sameElements Array("$ax", "2(bx)"))
  }

  test("testGetRg"){
    var rg = Encoder.getRg("ax")
    assert(rg == Register.AX.code)

    rg = Encoder.getRg("$bx")
    assert(rg == Register.BX.code)
  }

  test("testGetAddress") {
    val address = Encoder.getAddress("5($ax)")
    assert(address == (5, Register.AX.code))
  }

}
