package views.architecture

import controllers.architecture.ArchitectureController
import scalafx.scene.control.{Button, Tooltip}
import scalafxml.core.macros.sfxml
import base.Extensions.IntExtensions
import models.layout.SharedVariables

@sfxml
class ID_EX_View(
                  val wb: Button,
                  val m: Button,
                  val ex: Button,
                  val pc: Button,
                  val data1: Button,
                  val data2: Button,
                  val inst: Button,
                ) extends StateFul {
  SharedVariables.views.add(this)

  wb.setTooltip(new Tooltip("WB"))
  m.setTooltip(new Tooltip("M"))
  ex.setTooltip(new Tooltip("EX"))
  pc.setTooltip(new Tooltip("PC"))
  data1.setTooltip(new Tooltip("Register Output 1"))
  data2.setTooltip(new Tooltip("Register Output 2"))
  inst.setTooltip(new Tooltip("Instruction"))

  override def setState(architectureController: ArchitectureController): Unit = {
    val id_ex = architectureController.arch.id_ex
    wb.text = id_ex.wb.asInt intToBinString 2
    m.text = id_ex.m.asInt intToBinString 2
    ex.text = id_ex.ex.asInt intToBinString 2
    pc.text = id_ex.pc intToBinString 8
    data1.text = id_ex.data1 intToBinString 8
    data2.text = id_ex.data2 intToBinString 8
    inst.text = id_ex.inst.get intToHexString 32
  }
}
