package views.layout

import base.Observer
import models.architecture.Memory
import models.layout.DataEntity
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{ScrollPane, TableColumn, TableView}
import scalafx.scene.layout.VBox

class MemoryContentView(memo: Memory) extends ScrollPane with Observer{


  val data: Array[DataEntity] = memo.data.zipWithIndex.map(r => new DataEntity(r._2, r._1))

  memo.observer = Some(this)

  this.content = new VBox(){
    children.add(
      new TableView[DataEntity]() {
        editable = true
        columns ++= Seq(
          new TableColumn[DataEntity, Int]() {
            text = "Address"
            editable = true
            sortable = false
            prefWidth = 200
            cellValueFactory = {
              _.value.address
            }
            cellFactory = { _: TableColumn[DataEntity, Int] => new CellView[DataEntity,Int]() }
          },
          new TableColumn[DataEntity, Int]() {
            text = "Content"
            editable = true
            sortable = false
            prefWidth = 200
            cellValueFactory = {
              _.value.content
            }
            cellFactory = { _: TableColumn[DataEntity, Int] => new CellView[DataEntity,Int]() }
          }
        )
        items = new ObservableBuffer[DataEntity]() {
          addAll(data)
        }
      }
    )
  }

  override def update(idx: Int, value: Int): Unit = this.data(idx).content.set(value)

}