package controllers.architecture

import models.architecture.Codes.{OpCode, Register}
import models.architecture.Architecture
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuiteLike

class ArchitectureControllerTest extends AnyFunSuiteLike with BeforeAndAfterEach{

  var instructions: Array[Int] = Array.fill(10)(0)
  var data: Array[Int] = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
  var arch = new Architecture(instructions, data)
  var archController = new ArchitectureController(arch)

  override def beforeEach(): Unit = {
    super.beforeEach()
    instructions = Array.fill(10)(0)
    data = Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    arch = new Architecture(instructions, data)
    archController = new ArchitectureController(arch)
  }


  test("testFetch") {
    instructions(3) = 0xffa01

    arch.pc = 12
    var if_id = archController.fetch
    assert(arch.pc == 16)
    assert(if_id.pc == 16)
    assert(if_id.inst.get == instructions(3))

    arch.pc = 8
    arch.if_id.pc = 4
    arch.if_id.inst.setWith(opCode = OpCode.J.code, jOffset = 20)
    if_id = archController.fetch
    assert(arch.pc == 84)
    assert(if_id.pc == 0)

  }

  test("testDecode") {
    arch.if_id.inst.setWith(src_1 = Register.AX.code, src_2 = Register.BX.code)
    archController.registersControl.set(Register.AX.code, 15)
    archController.registersControl.set(Register.BX.code, -3)
    var id_ex = archController.decode
    assert(id_ex.inst.get == arch.if_id.inst.get)
    assert(id_ex.data1 == 15)
    assert(id_ex.data2 == -3)

    arch.if_id.inst.setWith(src_1 = Register.CX.code, src_2 = Register.BX.code)
    archController.registersControl.set(Register.CX.code, 10)
    archController.registersControl.set(Register.BX.code, -6)
    id_ex = archController.decode
    assert(id_ex.inst.get == arch.if_id.inst.get)
    assert(id_ex.data1 == 10)
    assert(id_ex.data2 == -6)


    arch.if_id.inst.setWith(src_1 = Register.CX.code, src_2 = Register.BX.code)
    arch.ex_mem.opCode = OpCode.BNE.code
    arch.ex_mem.aluZero = false
    id_ex = archController.decode
    assert(id_ex.inst.get == 0)
  }

  test("testExecute") {
    arch.id_ex.pc = 4
    arch.id_ex.inst.setWith(opCode = OpCode.BEQ.code, bOffset = 10)
    var ex_mem = archController.execute
     assert(ex_mem.nextPc == 44)

    arch.id_ex.inst.setWith(src_2 = 5, dDst = 3)
    arch.id_ex.ex.regDst = true
    ex_mem = archController.execute
    assert(ex_mem.dstRg == 3)


    arch.id_ex.inst.setWith(opCode = OpCode.ADDI.code)
    arch.ex_mem.opCode = OpCode.BNE.code
    arch.ex_mem.aluZero = false
    ex_mem = archController.execute
    assert(ex_mem.opCode == 0 && ex_mem.aluResult == 0)
  }

  test("testMemoryAccess") {
    arch.ex_mem.dstRg = 9
    arch.ex_mem.aluResult = 3
    arch.ex_mem.data2 = -1
    arch.ex_mem.m.memRead = true
    var mem_wb = archController.memoryAccess
    assert(mem_wb.memData == data(3))
    assert(data(3) == 3)
    assert(mem_wb.dstRg == arch.ex_mem.dstRg)

    arch.ex_mem.aluResult = 3
    arch.ex_mem.data2 = -1
    arch.ex_mem.m.memWrite = true
    arch.ex_mem.m.memRead = false
    mem_wb = archController.memoryAccess
    assert(mem_wb.memData == 0)
    assert(data(3) == -1)
  }

}
