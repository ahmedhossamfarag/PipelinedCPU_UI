package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Label, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import models.layout.SharedVariables
@sfxml
class RegistersView(
                   val src1: Label,
                   val src2: Label,
                   val output1: Label,
                   val output2: Label
                   ) extends StateFul {

  SharedVariables.views.add(this)

  src1.setTooltip(new Tooltip("Src1"))
  src2.setTooltip(new Tooltip("Src2"))

  override def setState(architectureController: ArchitectureController): Unit = {

    val arch = architectureController.arch
    src1.text = arch.if_id.inst.src_1 intToBinString 3
    src2.text = arch.if_id.inst.src_2 intToBinString 3
    output1.text = architectureController.registersControl.get(arch.if_id.inst.src_1).getOrElse(0) intToHexString 32
    output2.text = architectureController.registersControl.get(arch.if_id.inst.src_2).getOrElse(0) intToHexString 32
  }
}
