package models.architecture

object Control {
  class WB{
    var regWrite: Boolean = false
    var memoToReg: Boolean = false
  }
  class M{
    var memWrite: Boolean = false
    var memRead: Boolean = false
  }
  class EX{
    var aluSrc: Boolean = false
    var regDst: Boolean = true
  }

  class CMap{
    var wb: WB = new WB()
    var m: M = new M()
    var ex: EX = new EX()
  }
}
