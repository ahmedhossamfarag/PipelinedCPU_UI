package controllers.architecture

import models.architecture.Codes.OpCode
import models.architecture.{Architecture, Codes}
import models.architecture.Codes.OpCode._
import models.architecture.signals.SignalsController
class BranchControl(val arch: Architecture) extends SignalsController{
  def willBranch: Boolean = {
    val code = Codes.OpCode.fromCode(arch.ex_mem.opCode)

    if (code.isEmpty) return false

    val zero = arch.ex_mem.aluZero
    code.get match {
      case BEQ => zero
      case BNE => !zero
      case _ => false
    }
  }

  def willJump: Boolean = {
    val code = Codes.OpCode.fromCode(arch.if_id.inst.opCode)

    code match {
      case Some(OpCode.J) => true
      case _ => false
    }
  }

  def nextPc: Int = {
    if(arch.branchSignals.willBranch) return  arch.ex_mem.nextPc

    if(arch.branchSignals.willJump) return arch.if_id.pc + (arch.if_id.inst.jOffset << 2)

    arch.pc + 4
  }

  override def updateSignals(): Unit = {
    arch.branchSignals.willBranch = willBranch
    arch.branchSignals.willJump = willJump
  }

}
