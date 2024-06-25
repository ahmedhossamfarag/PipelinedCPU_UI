package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Button, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions._
import models.layout.SharedVariables
@sfxml
class EX_MEM_View(
                   val wb: Button,
                   val m: Button,
                   val nextPc: Button,
                   val opCode: Button,
                   val aluZero: Button,
                   val aluResult: Button,
                   val data2: Button,
                   val dstRg: Button,
                 ) extends StateFul {
  SharedVariables.views.add(this)


  wb.setTooltip(new Tooltip("WB"))
  m.setTooltip(new Tooltip("M"))
  nextPc.setTooltip(new Tooltip("Next PC"))
  opCode.setTooltip(new Tooltip("Op Code"))
  aluZero.setTooltip(new Tooltip("ALU Zero"))
  aluResult.setTooltip(new Tooltip("ALU Output"))
  data2.setTooltip(new Tooltip("Registers Output 2"))
  dstRg.setTooltip(new Tooltip("Register Dst"))


  override def setState(architectureController: ArchitectureController): Unit = {
    val ex_mem = architectureController.arch.ex_mem
    wb.text = ex_mem.wb.asInt intToBinString 2
    m.text = ex_mem.m.asInt intToBinString 2
    nextPc.text = ex_mem.nextPc intToHexString 32
    opCode.text = ex_mem.opCode intToBinString 3
    aluZero.text = ex_mem.aluZero.asInt intToBinString 1
    aluResult.text = ex_mem.aluResult intToHexString 32
    data2.text = ex_mem.data2 intToHexString 32
    dstRg.text = ex_mem.dstRg intToBinString 3
  }
}
