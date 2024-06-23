package controllers.architecture

import org.scalatest.funsuite.AnyFunSuiteLike
import models.architecture.Architecture
import models.architecture.Codes.OpCode

class ControlMapTest extends AnyFunSuiteLike {

  test("testMap") {
    val arch = new Architecture(Array.empty, Array.empty)
    val controlMap = new ControlMap(arch)

    arch.if_id.inst.setWith(opCode = OpCode.BEQ.code)
    var map = controlMap.map
    assert(!map.wb.regWrite)
    assert(!map.m.memWrite && !map.m.memRead)
    assert(!map.ex.aluSrc)

    arch.if_id.inst.setWith(opCode = OpCode.ALU.code)
    map = controlMap.map
    assert(map.wb.regWrite && map.wb.memoToReg)
    assert(!map.m.memWrite && !map.m.memRead)
    assert(!map.ex.aluSrc && map.ex.regDst)

    arch.if_id.inst.setWith(opCode = OpCode.LW.code)
    map = controlMap.map
    assert(map.wb.regWrite && !map.wb.memoToReg)
    assert(!map.m.memWrite && map.m.memRead)
    assert(map.ex.aluSrc)

  }

}
