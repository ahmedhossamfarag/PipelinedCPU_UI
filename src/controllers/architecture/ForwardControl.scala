package controllers.architecture

import models.architecture.Architecture
import models.architecture.Codes.OpCode
import models.architecture.Codes.OpCode._
import models.architecture.signals.ForwardType._
import models.architecture.signals.SignalsController

class ForwardControl(archController: ArchitectureController) extends SignalsController{
  val arch: Architecture = archController.arch

  private def getRgForwardType(src: Int): ForwardType = {
    var code = OpCode.fromCode(arch.id_ex.inst.opCode)
    if (code.isDefined) {
      code.get match {
        case ALU | ADDI =>
          val rgDst = if (arch.id_ex.ex.regDst) arch.id_ex.inst.dDst else arch.id_ex.inst.iDst
          if (rgDst == src) return ALUOutput
        case _ =>
      }
    }

    code = OpCode.fromCode(arch.ex_mem.opCode)
    if (code.isDefined) {
      val rgDst = arch.ex_mem.dstRg
      if (rgDst == src) {
        code.get match {
          case ALU | ADDI =>
            return EX_MEM_ALUResult
          case LW =>
            return MemoryOutput
          case _ =>
        }
      }
    }


    if (arch.mem_wb.wb.regWrite && arch.mem_wb.dstRg == src)
      return RegistersWriteData


    NONE
  }

  private def getAlUSrcForwardType(src: Int): ForwardType = {
    val code = OpCode.fromCode(arch.ex_mem.opCode)
    if (code.isDefined) {
      val rgDst = arch.ex_mem.dstRg
      if (rgDst == src) {
        code.get match {
          case LW =>
            return MemoryOutput
          case _ =>
        }
      }
    }

    NONE
  }

  def read(forwardType: ForwardType): Option[Int] = {
    forwardType match {
      case ALUOutput => Some(archController.alu.output)
      case EX_MEM_ALUResult => Some(arch.ex_mem.aluResult)
      case MemoryOutput => Some(arch.memory.get(arch.ex_mem.aluResult))
      case RegistersWriteData => Some(if (arch.mem_wb.wb.memoToReg) arch.mem_wb.aluResult else arch.mem_wb.memData)
      case _ => None
    }
  }

  override def updateSignals(): Unit = {
    arch.forwardSignals.readData1Signal = getRgForwardType(arch.if_id.inst.src_1)
    arch.forwardSignals.readData2Signal = getRgForwardType(arch.if_id.inst.src_2)
    arch.forwardSignals.aluSrc1Signal = getAlUSrcForwardType(arch.id_ex.inst.src_1)
    arch.forwardSignals.aluSrc2Signal = getAlUSrcForwardType(arch.id_ex.inst.src_2)
  }
}
