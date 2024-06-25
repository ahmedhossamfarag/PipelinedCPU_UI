package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Button, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import models.layout.SharedVariables

@sfxml
class IF_ID_View (
                 val pc: Button,
                 val inst: Button
                 ) extends StateFul {
  SharedVariables.views.add(this)

  pc.setTooltip(new Tooltip("PC"))
  inst.setTooltip(new Tooltip("Instruction"))

  override def setState(architectureController: ArchitectureController): Unit = {
    val if_id = architectureController.arch.if_id
    pc.text = if_id.pc intToBinString 8
    inst.text = if_id.inst.get intToHexString 32
  }
}
