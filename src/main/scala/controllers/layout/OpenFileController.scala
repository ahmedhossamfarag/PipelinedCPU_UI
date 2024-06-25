package controllers.layout

import views.layout.OpenFileView
import java.io.File
import scalafx.stage.FileChooser
import models.layout.SharedVariables

class OpenFileController(val view:OpenFileView){

  var error: (Boolean, Boolean) = (true, false)

  view.instFileInput.text.onChange { (_, _, newValue) =>
    val file = new File(newValue)
    if (file.exists() && file.isFile) {
      error = (false, error._2)
      setOkButton()
      view.instFileError.text = ""
    } else {
      error = (true, error._2)
      setOkButton()
      view.instFileError.text = "File Not Found"
    }
  }

  view.dataFileInput.text.onChange { (_, _, newValue) =>
    val file = new File(newValue)
    if (newValue.isBlank || (file.exists() && file.isFile)) {
      error = (error._1, false)
      setOkButton()
      view.dataFileError.text = ""
    } else {
      error = (error._1, true)
      setOkButton()
      view.dataFileError.text = "File Not Found"
    }
  }


  view.instFileExplorer.setOnMouseClicked { _ =>
    openFileDialog("Choose Instructions File").foreach{view.instFileInput.text = _}
  }

  view.dataFileExplorer.setOnMouseClicked { _ =>
    openFileDialog("Choose Data File").foreach{view.dataFileInput.text = _}
  }

  private def setOkButton(): Unit = {
    view.okButton.foreach {
      _.setDisable(error._1 || error._2)
    }
  }

  private def openFileDialog(title: String): Option[String] = {
    val chooser = new FileChooser()
    chooser.title = title
    chooser.initialDirectory = SharedVariables.defaultDir
    val file = chooser.showOpenDialog(SharedVariables.stage)
    if (file != null) {
      SharedVariables.defaultDir = file.getParentFile
      Some(file.getPath)
    }
    else
      None
  }

}
