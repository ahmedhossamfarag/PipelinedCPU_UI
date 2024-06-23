package controllers.architecture

import models.architecture.Architecture
import models.architecture.Codes.OpCode
import models.architecture.Codes.OpCode._

class ForwardControl(archController: ArchitectureController){
  val arch: Architecture = archController.arch
  def forwardRgRead(src: Int) : Option[Int] = {
    var code = OpCode.fromCode(arch.id_ex.inst.opCode)
    if(code.isDefined){
      code.get match {
        case ALU | ADDI => {
          val rgDst = if (arch.id_ex.ex.regDst) arch.id_ex.inst.dDst else arch.id_ex.inst.iDst
          if (rgDst == src) return Some(archController.alu.output)
        }
        case _ =>
      }
    }

    code = OpCode.fromCode(arch.ex_mem.opCode)
    if (code.isDefined){
      val rgDst = arch.ex_mem.dstRg
      if (rgDst == src) {
        code.get match {
          case ALU | ADDI =>
            return Some(arch.ex_mem.aluResult)
          case LW =>
            return  Some(arch.memory.get(arch.ex_mem.aluResult))
          case _ =>
        }
      }
    }


    if (arch.mem_wb.wb.regWrite && arch.mem_wb.dstRg == src)
      return Some(if (arch.mem_wb.wb.memoToReg) arch.mem_wb.aluResult else arch.mem_wb.memData)


    None
  }

  def forwardALUSrc(src: Int) : Option[Int] = {
    val code = OpCode.fromCode(arch.ex_mem.opCode)
    if (code.isDefined) {
      val rgDst = arch.ex_mem.dstRg
      if (rgDst == src) {
        code.get match {
          case LW =>
            return Some(arch.memory.get(arch.ex_mem.aluResult))
          case _ =>
        }
      }
    }

    None
  }
}
