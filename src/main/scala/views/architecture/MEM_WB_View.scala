package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Button, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import models.layout.SharedVariables
@sfxml
class MEM_WB_View(
                   val wb: Button,
                   val memData: Button,
                   val aluResult: Button,
                   val dstRg: Button
                 ) extends StateFul {
  SharedVariables.views.add(this)

  wb.setTooltip(new Tooltip("WB"))
  memData.setTooltip(new Tooltip("Memory Output"))
  aluResult.setTooltip(new Tooltip("ALU Output"))
  dstRg.setTooltip(new Tooltip("Register Dst"))

  override def setState(architectureController: ArchitectureController): Unit = {
    val mem_wb = architectureController.arch.mem_wb
    wb.text = mem_wb.wb.asInt intToBinString 2
    memData.text = mem_wb.memData intToHexString 32
    aluResult.text = mem_wb.aluResult intToHexString 32
    dstRg.text =  mem_wb.dstRg intToBinString 3
  }
}
