package views.layout

import base.Observer
import models.architecture.Codes.Register
import models.architecture.{Memory, Registers}
import models.layout.RegEntity
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ScrollPane, TableColumn, TableView}
import scalafx.scene.layout.VBox

class RegContentView(registers: Registers) extends ScrollPane with Observer{

  val data: Array[RegEntity] = registers.data.zipWithIndex.map(r => new RegEntity(Register.atIndex(r._2).asString, r._1))

  registers.observer = Some(this)

  this.content = new VBox(){
    children.add(
      new TableView[RegEntity]() {
        editable = true
        columns ++= Seq(
          new TableColumn[RegEntity, String]() {
            text = "Name"
            editable = true
            sortable = false
            prefWidth = 200
            cellValueFactory = {
              _.value.name
            }
            cellFactory = { _: TableColumn[RegEntity, String] => new CellView[RegEntity,String]() }
          },
          new TableColumn[RegEntity, Int]() {
            text = "Content"
            editable = true
            sortable = false
            prefWidth = 200
            cellValueFactory = {
              _.value.content
            }
            cellFactory = { _: TableColumn[RegEntity, Int] => new CellView[RegEntity,Int]() }
          }
        )
        items = new ObservableBuffer[RegEntity]() {
          addAll(data)
        }
      }
    )
  }

  override def update(idx: Int, value: Int): Unit = this.data(idx).content.set(value)

}