package models.architecture

import base.Observer

class Memory(val data: Array[Int]){

  val cleanData: Array[Int] = data.clone()

  var observer: Option[Observer] = None

  def size: Int = data.length

  def get(address: Int): Int = data(address%data.length)
  def set(address: Int)(value: Int): Unit = {
    data(address % data.length) = value
    observer.foreach(_.update(address, value))
  }

  override def toString: String = data.mkString(" , ")
}
