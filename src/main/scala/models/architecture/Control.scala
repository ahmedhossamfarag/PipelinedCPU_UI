package models.architecture
import base.Extensions.BooleanExtensions

object Control {
  class WB{
    var regWrite: Boolean = false
    var memoToReg: Boolean = false

    def asInt: Int = (regWrite.asInt << 1) | memoToReg.asInt
  }
  class M{
    var memWrite: Boolean = false
    var memRead: Boolean = false

    def asInt: Int = (memRead.asInt << 1) | memWrite.asInt
  }
  class EX{
    var aluSrc: Boolean = false
    var regDst: Boolean = true

    def asInt: Int = (regDst.asInt << 1) | aluSrc.asInt
  }
}
