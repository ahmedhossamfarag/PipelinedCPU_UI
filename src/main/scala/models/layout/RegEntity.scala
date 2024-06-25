package models.layout

import scalafx.beans.property.{ObjectProperty, StringProperty}

class RegEntity(_name: String, _content: Int) {
  val name = new StringProperty(this, "address", _name)
  val content = new ObjectProperty[Int](this, "content", _content)
}
