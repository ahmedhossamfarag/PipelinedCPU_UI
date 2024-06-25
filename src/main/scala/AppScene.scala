import models.layout.SharedVariables
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafx.Includes._


object AppScene extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Pipelined CPU"
      scene = new Scene (FXMLView(getClass.getResource("layout/main-view.fxml"), NoDependencyResolver))
    }
    SharedVariables.stage = stage
  }
}