package controllers.architecture

import models.architecture.{Architecture, Codes}
import models.architecture.Codes.OpCode._
class BranchControl(val arch: Architecture){
  def will: Boolean = {
    val code = Codes.OpCode.fromCode(arch.ex_mem.opCode)

    if(code.isEmpty) return false

    val zero = arch.ex_mem.aluZero
    code.get match {
      case BEQ => zero
      case  BNE => !zero
      case J => true
      case _ => false
    }
  }
}
