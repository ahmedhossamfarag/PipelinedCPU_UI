package views.layout

import base.Extensions.IntExtensions
import scalafx.geometry.Pos
import scalafx.scene.control.TableCell
import scalafx.scene.paint.Color
import scalafx.scene.text.TextAlignment

class CellView[X,Y]() extends TableCell[X,Y]{
  textAlignment = TextAlignment.Center
  alignment = Pos.Center
  item.onChange{(_,_,newVal) =>
    text = newVal match {
      case s: String => s
      case i: Int => f"${i intToHexString 32} (${i})"
      case _ => ""
    }
  }
}
