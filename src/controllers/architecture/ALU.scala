package controllers.architecture

import base.Extensions.IntExtensions
import models.architecture.Codes.ALUCode._
import models.architecture.Codes.OpCode._
import models.architecture.{Architecture, Codes}

class ALU(val arch: Architecture){

  private def getCode: Option[Codes.ALUCode] = {
    val op = Codes.OpCode.fromCode(arch.id_ex.inst.opCode)

    if (op.isEmpty) return None

    op.get match {
      case LW | SW | ADDI => Some(ADD)
      case BEQ | BEQ => Some(SUB)
      case _ => Codes.ALUCode.fromCode(arch.id_ex.inst.aluCode)
    }
  }

  def output: Int = {
    val code = getCode

    if(code.isEmpty) return 0

    val arg1 = arch.id_ex.data1
    val arg2 = if(arch.id_ex.ex.aluSrc) arch.id_ex.inst.immediate else arch.id_ex.data2
    code.get match {
      case ADD  => arg1 + arg2
      case SUB  => arg1 - arg2
      case AND  => arg1 & arg2
      case NOR  => ~(arg1 | arg2)
      case SLT  => if (arg1 < arg2) 1 else 0
      case SLTU => if (arg1.toUInt < arg2.toUInt) 1 else 0
      case _    => 0
    }
  }
}
