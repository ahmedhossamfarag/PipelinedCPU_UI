package models.architecture

class Architecture(instructions: Array[Int], data: Array[Int]) {
  var pc : Int = 0
  val insMemory: Memory = new Memory(instructions)
  var if_id : IF_ID = new IF_ID()
  val registers : Registers = new Registers()
  var id_ex : ID_EX = new ID_EX()
  var ex_mem : EX_MEM = new EX_MEM()
  val memory: Memory = new Memory(data)
  var mem_wb : MEM_WB = new MEM_WB()
}
