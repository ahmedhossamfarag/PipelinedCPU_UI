package controllers.architecture

import models.architecture.{Architecture, Codes, Control}
import models.architecture.Codes.OpCode._

class ControlMap(arch: Architecture){

  private def mapEX(code: Codes.OpCode)(map: Control.CMap): Unit = {
    map.ex.aluSrc = code match {
      case LW | SW | ADDI => true
      case _ => false
    }
    map.ex.regDst = code match {
      case ALU => true
      case _ => false
    }
  }

  private def mapM(code: Codes.OpCode)(map: Control.CMap): Unit = {
    map.m.memRead = code match {
      case LW => true
      case _ => false
    }
    map.m.memWrite = code match {
      case SW => true
      case _ => false
    }
  }

  private def mapWB(code: Codes.OpCode)(map: Control.CMap): Unit = {
    map.wb.regWrite = code match {
      case ALU | ADDI | LW => true
      case _ => false
    }
    map.wb.memoToReg = code match {
      case LW => false
      case _ => true
    }
  }

  def map: Control.CMap = {
    val map = new Control.CMap()
    val code = Codes.OpCode.fromCode(arch.if_id.inst.opCode)

    if (code.isEmpty) return map

    mapEX(code.get)(map)
    mapM(code.get)(map)
    mapWB(code.get)(map)

    map
  }

}
