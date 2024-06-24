package base

object Extensions{
  implicit class IntExtensions(x: Int) {
    def range(r: (Int,Int)): Int = (x >> r._1) & ((1 << (r._2 - r._1 + 1)) - 1)

    def at(i: Int): Int = (x >> i) & 1

    def extend(i: Int): Int = if(x.at(i) == 0) x.range((0,i)) else (-1 << i) | x.range((0,i))

    def toUInt: Long = x & 0xFFFFFFFFL
  }
}

