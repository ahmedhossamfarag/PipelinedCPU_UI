package controllers.layout

import base.Builder
import models.layout.SharedVariables
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType, Dialog, TextField}
import views.layout.{MemoryContentView, OpenFileView, RegContentView, Window}

class MainController {

  private var runController: Option[RunController] = None

  private var dataMemoContentView: Option[Window] = None
  private var instMemoContentView: Option[Window] = None
  private var regContentView: Option[Window] = None

  private var preferredPeriod: Option[Int] = None

  def newArchitecture(): Boolean = {
    hideWindows()
    val openFileView = new OpenFileView()
    val dialog = new Dialog[Boolean](){
      title = "Choose Files"
      dialogPane().setContent(openFileView)
      dialogPane().getButtonTypes.addAll(ButtonType.OK, ButtonType.Cancel)
      resultConverter = {b => b == ButtonType.OK}
    }
    openFileView.okButton = Some(dialog.dialogPane().lookupButton(ButtonType.OK).asInstanceOf[javafx.scene.control.Button])
    openFileView.okButton.foreach(_.setDisable(true))
    dialog.showAndWait() match {
      case Some(true) =>
        val result = openFileView.get
        if(!newArchitecture(result._1, result._2)) return false
        closeWindows()
        true
      case _ => false
    }
  }

  def newArchitecture(instructionsFile: String, dataFile: Option[String] = None): Boolean = {
    try{
      val archController = Builder.buildFiles(instructionsFile, dataFile)
      runController = Some(new RunController(archController))
      runController.foreach(_.adaptState())
      true
    }catch {
      case e:Exception =>
        e.printStackTrace()
        showErrorAlert(e.getMessage)
        false
    }
  }


  private def showErrorAlert(error: String): Unit = {
    new Alert(AlertType.Error) {
      initOwner(SharedVariables.stage)
      title = "Error"
      headerText = "Error Alert"
      contentText = error
    }.showAndWait()
  }

  def getFixedRatePeriod: Option[Int] = {
    val textField = new TextField() {
      promptText = "Enter Period In Milliseconds"
      text = preferredPeriod.map(_.toString).getOrElse("")
      text.addListener((_, oldValue, newValue) => {
        if(newValue.nonEmpty){
          if(newValue.toIntOption.isEmpty) text = oldValue
        }
      })
    }
    val dialog = new Dialog[Boolean]() {
      title = "Enter Period In Milliseconds"
      dialogPane().setContent(textField)
      dialogPane().getButtonTypes.addAll(ButtonType.OK, ButtonType.Cancel)
      resultConverter = _ == ButtonType.OK
    }
    dialog.showAndWait() match {
      case Some(true) =>
        preferredPeriod = textField.text().toIntOption
        preferredPeriod
      case _ => None
    }
  }

  def step(): Boolean = runController.exists(_.step())

  def proceed(): Boolean =  runController.exists(_.proceed())

  def viewDataMemoryContent():Unit = {
    if(dataMemoContentView.isEmpty)
      runController.foreach{ r =>
        val window = new Window("Data Memory", new MemoryContentView(r.getArchitecture.memory))
        dataMemoContentView = Some(window)
      }
    else
      dataMemoContentView.foreach(_.show())
  }

  def viewInstMemoryContent():Unit = {
    if(instMemoContentView.isEmpty)
      runController.foreach{ r =>
        val window = new Window("Instructions Memory", new MemoryContentView(r.getArchitecture.insMemory))
        instMemoContentView = Some(window)
      }
    else
      instMemoContentView.foreach(_.show())
  }

  def viewRegContent(): Unit = {
    if(regContentView.isEmpty)
      runController.foreach { r =>
        val window = new Window("Registers Content", new RegContentView(r.getArchitecture.registers))
        regContentView = Some(window)
      }
    else
      regContentView.foreach(_.show())
  }

  private def hideWindows(): Unit = {
    dataMemoContentView.foreach(_.hide())
    instMemoContentView.foreach(_.hide())
    regContentView.foreach(_.hide())
  }


  private def closeWindows(): Unit = {
    dataMemoContentView.foreach(_.close())
    dataMemoContentView = None
    instMemoContentView.foreach(_.close())
    instMemoContentView = None
    regContentView.foreach(_.close())
    regContentView = None
  }

  def reset(): Unit = {
    runController.foreach(_.reset())
    closeWindows()
  }

}
