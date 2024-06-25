package base

object Extensions{
  implicit class IntExtensions(x: Int) {
    def range(r: (Int,Int)): Int = (x >> r._1) & ((1 << (r._2 - r._1 + 1)) - 1)

    def at(i: Int): Int = (x >> i) & 1

    def extend(i: Int): Int = if(x.at(i) == 0) x.range((0,i)) else (-1 << i) | x.range((0,i))

    def toUInt: Long = x & 0xFFFFFFFFL

    def intToBinString(l: Int): String = {
      val s = x.toBinaryString
      val b = if(x >= 0) "0" else "1"
      if(s.length > l) s.substring(s.length-l,s.length) else  b.repeat(l-s.length) + s
    }


    def intToHexString(l: Int): String = {
      val _l = math.ceil(l/4.0).toInt
      val s = x.toHexString
      val b = if (x >= 0) "0" else "f"
      (if (s.length > _l) s.substring(s.length-_l,s.length) else b.repeat(_l - s.length) + s).toUpperCase()
    }
  }

  implicit class BooleanExtensions(x:Boolean) {
    def asInt: Int = if(x) 1 else 0
  }
}

