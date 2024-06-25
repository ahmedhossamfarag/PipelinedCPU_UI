package views.layout

import controllers.layout.OpenFileController
import scalafx.geometry.{Insets, Orientation}
import scalafx.scene.control.{Button, Label, Separator, TextField}
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

class OpenFileView extends VBox{

  var instFileInput: TextField = new TextField(){
    hgrow = Priority.Always
  }
  var instFileExplorer: Button = new Button("Open Explorer")
  var instFileError: Label = new Label(){
    textFill = Color.Red
  }
  var dataFileInput: TextField = new TextField(){
    hgrow = Priority.Always
  }
  var dataFileExplorer: Button = new Button("Open Explorer")
  var dataFileError: Label = new Label(){
    textFill = Color.Red
  }
  
  var okButton: Option[javafx.scene.control.Button] = None
  new OpenFileController(this)
  this.padding = Insets(5)
  this.prefWidth = 500
  this.children = Seq(
    new Label("Instruction File"){font = Font.font(size = 15)},
    new HBox(){
      children = Seq(
        instFileInput,
        instFileExplorer
      )
    },
    instFileError,
    new Separator(){
      orientation = Orientation.Vertical
      prefHeight = 10
    },
    new Label("Data File"){font = Font.font(size = 15)},
    new HBox(){
      children = Seq(
        dataFileInput,
        dataFileExplorer
      )
    },
    dataFileError,
    new Separator(){
      orientation = Orientation.Vertical
      prefHeight = 20
    }
  )






  def get: (String, Option[String]) = (instFileInput.text.value, if(dataFileInput.text.value.isBlank) None else Some(dataFileInput.text.value))
}
