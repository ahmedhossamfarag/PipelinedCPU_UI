package models.architecture

class MEM_WB {
  var pc:  Int = 0
  var wb: Control.WB = new Control.WB()
  var memData: Int = 0
  var aluResult: Int = 0
  var dstRg: Int = 0
}
