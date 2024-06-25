package models.layout

import scalafx.beans.property.ObjectProperty

class DataEntity(_address: Int, _content: Int) {
  val address = new ObjectProperty[Int](this, "address", _address)
  val content = new ObjectProperty[Int](this, "content", _content)
}
