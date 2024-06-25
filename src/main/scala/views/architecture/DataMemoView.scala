package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Label, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import models.layout.SharedVariables

@sfxml
class DataMemoView(
                  val output: Label
                  ) extends StateFul {
  SharedVariables.views.add(this)

  output.setTooltip(new Tooltip("Memory Output"))

  override def setState(architectureController: ArchitectureController): Unit = {
    output.text = architectureController.arch.memory.get(math.abs(architectureController.arch.ex_mem.aluResult)) intToHexString 32
  }
}
