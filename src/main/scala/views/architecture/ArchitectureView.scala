package views.architecture

import controllers.architecture.ArchitectureController
import base.Extensions.IntExtensions
import javafx.fxml.{FXML, Initializable}
import javafx.scene.control.{Button, Label, Tooltip}
import javafx.scene.layout.{BorderPane, HBox, Pane}
import models.layout.SharedVariables

import java.net.URL
import java.util.ResourceBundle

class ArchitectureView(d :scalafxml.core.ControllerDependencyResolver) extends StateFul with Initializable{

  @FXML
  var pc: Button = _
  @FXML
  var instMemoView: BorderPane = _
  @FXML
  var if_id_View: HBox = _
  @FXML
  var registersView: BorderPane = _
  @FXML
  var id_ex_View: HBox = _
  @FXML
  var aluView: Pane = _
  @FXML
  var ex_mem_View: HBox = _
  @FXML
  var dataMemoView: BorderPane = _
  @FXML
  var mem_wb_View: HBox = _
  @FXML
  var if_id_label: Label = _
  @FXML
  var id_ex_label: Label = _
  @FXML
  var ex_mem_label: Label = _
  @FXML
  var mem_wb_label: Label = _


  SharedVariables.views.add(this)

  SharedVariables.architectureView = this


  override def setState(architectureController: ArchitectureController): Unit = {
    val arch = architectureController.arch
    pc.setText(arch.pc intToBinString 8)
    if_id_label.setText(getLine(arch.if_id.pc).toString)
    id_ex_label.setText(getLine(arch.id_ex.pc).toString)
    ex_mem_label.setText(getLine(arch.ex_mem.pc).toString)
    mem_wb_label.setText(getLine(arch.mem_wb.pc).toString)
  }


  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit = {

    pc.setTooltip(new Tooltip("PC"))
  }

  private def getLine(pc: Int): Int = pc / 4
}
