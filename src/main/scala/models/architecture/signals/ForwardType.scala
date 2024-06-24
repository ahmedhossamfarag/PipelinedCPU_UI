package models.architecture.signals

object ForwardType extends Enumeration {
  type ForwardType = Value
  val ALUOutput, EX_MEM_ALUResult, MemoryOutput, RegistersWriteData, NONE = Value
}
