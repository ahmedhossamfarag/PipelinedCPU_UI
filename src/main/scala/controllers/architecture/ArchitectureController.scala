package controllers.architecture

import models.architecture.signals.SignalsController
import models.architecture.{Architecture, EX_MEM, ID_EX, IF_ID, MEM_WB}

class ArchitectureController(val arch: Architecture) extends SignalsController{
  val registersControl = new RegistersControl(arch.registers)
  val alu = new ALU(this)
  val controlMap = new ControlMap(arch)
  val branchControl = new BranchControl(arch)
  val forwardControl = new ForwardControl(this)

  def fetch: IF_ID = {
    val if_id = new IF_ID()

    if(!arch.branchSignals.willBranch & !arch.branchSignals.willJump) {
      if_id.pc = arch.pc + 4
      if_id.inst.set(arch.insMemory.get(arch.pc / 4))
    }

    arch.pc = branchControl.nextPc

    if_id
  }

  def decode: ID_EX = {
    val id_ex = new ID_EX()

    if(!arch.branchSignals.willBranch) {
      id_ex.wb = arch.controlSignals.wb
      id_ex.m = arch.controlSignals.m
      id_ex.ex = arch.controlSignals.ex

      id_ex.pc = arch.if_id.pc

      id_ex.data1 = forwardControl.read(arch.forwardSignals.readData1Signal)
        .getOrElse(registersControl.get(arch.if_id.inst.src_1).getOrElse(0))
      id_ex.data2 = forwardControl.read(arch.forwardSignals.readData2Signal)
        .getOrElse(registersControl.get(arch.if_id.inst.src_2).getOrElse(0))

      id_ex.inst = arch.if_id.inst
    }

    id_ex
  }

  def execute: EX_MEM = {
    val ex_mem = new EX_MEM()

    if(!arch.branchSignals.willBranch) {
      ex_mem.wb = arch.id_ex.wb
      ex_mem.m = arch.id_ex.m

      ex_mem.pc = arch.id_ex.pc

      ex_mem.opCode = arch.id_ex.inst.opCode

      ex_mem.nextPc = arch.id_ex.pc + (arch.id_ex.inst.bOffset << 2)

      ex_mem.aluResult = alu.output
      ex_mem.aluZero = ex_mem.aluResult == 0

      ex_mem.data2 = forwardControl.read(arch.forwardSignals.aluSrc2Signal).getOrElse(arch.id_ex.data2)

      ex_mem.dstRg = if (arch.id_ex.ex.regDst) arch.id_ex.inst.dDst else arch.id_ex.inst.iDst
    }

    if(arch.mem_wb.wb.regWrite){
      registersControl.set(
        arch.mem_wb.dstRg,
        if(arch.mem_wb.wb.memoToReg) arch.mem_wb.aluResult else arch.mem_wb.memData
      )
    }

    ex_mem
  }

  def memoryAccess: MEM_WB = {
    val mem_wb = new MEM_WB()

    mem_wb.wb = arch.ex_mem.wb

    mem_wb.pc = arch.ex_mem.pc

    mem_wb.memData = if(arch.ex_mem.m.memRead) arch.memory.get(arch.ex_mem.aluResult) else 0

    mem_wb.aluResult = arch.ex_mem.aluResult

    mem_wb.dstRg = arch.ex_mem.dstRg

    if(arch.ex_mem.m.memWrite){
      arch.memory.set(arch.ex_mem.aluResult)(arch.ex_mem.data2)
    }

    mem_wb
  }

  def performCycle(): Unit = {
    val  if_id = fetch
    val id_ex = decode
    val ex_mem = execute
    val mem_wb = memoryAccess

    arch.if_id = if_id
    arch.id_ex = id_ex
    arch.ex_mem = ex_mem
    arch.mem_wb = mem_wb

    updateSignals()
  }

  override def updateSignals(): Unit = {
    controlMap.updateSignals()
    branchControl.updateSignals()
    forwardControl.updateSignals()
    alu.updateSignals()
  }
}
