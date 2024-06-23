package models.architecture

class EX_MEM {
  var wb: Control.WB = new Control.WB()
  var m: Control.M = new Control.M()
  var nextPc: Int = 0
  var opCode: Int = 0
  var aluZero: Boolean = false
  var aluResult: Int = 0
  var data2: Int = 0
  var dstRg: Int = 0
}
