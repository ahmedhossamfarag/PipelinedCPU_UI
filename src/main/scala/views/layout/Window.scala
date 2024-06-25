package views.layout

import javafx.stage.Stage
import scalafx.scene.{Parent, Scene}

class Window(title: String, root: Parent) extends Stage{
  setTitle(title)
  setScene(new Scene(root))
  setResizable(false)
  setAlwaysOnTop(true)
  show()
}
