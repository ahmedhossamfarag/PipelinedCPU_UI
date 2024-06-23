package base

import base.Extensions.IntExtensions
import org.scalatest.funsuite.AnyFunSuiteLike

class ExtensionsTest extends AnyFunSuiteLike {

  test("testIntRange") {
    var x = Integer.parseInt("10010101", 2)
    assert((x range (1,3)) == 2)
    assert((x range (2,4)) == 5)

    x = 0xf << 25
    assert((x range (25,31) ) == 0xf)
  }

  test("testIntAt"){
    val x = Integer.parseInt("10010101", 2)
    assert((x at 2) == 1)
    assert((x at 5) == 0)
  }

  test("testIntExtend"){
    val x = Integer.parseInt("10010101", 2)
    assert((x extend 2) == -3)
    assert((x extend  5) == Integer.parseInt("10101", 2))
  }

  test("testIntToUInt"){
    var x = -1
    assert(x.toUInt == 0x0ffffffffL)
    x = 0xff
    assert(x.toUInt == 0xff)
  }

}
