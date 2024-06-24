package models.architecture

class Memory(private val data: Array[Int]){
  def size: Int = data.length

  def get(address: Int): Int = data(address%data.length)
  def set(address: Int)(value: Int): Unit = data(address%data.length) = value

  override def toString: String = data.mkString(" , ")
}
