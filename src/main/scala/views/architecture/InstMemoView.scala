package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.Label
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import javafx.scene.control.Tooltip
import models.layout.SharedVariables
@sfxml
class InstMemoView(
                  val output: Label
                  ) extends StateFul {
  SharedVariables.views.add(this)

  output.setTooltip(new Tooltip("Instruction Memory Output"))

  override def setState(architectureController: ArchitectureController): Unit = {
    output.text = architectureController.arch.insMemory.get(architectureController.arch.if_id.pc) intToHexString 32
  }
}
