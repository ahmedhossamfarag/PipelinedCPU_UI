package models.architecture

import base.Extensions.IntExtensions

class Instruction {
  private var value: Int = 0

  def get: Int = value

  def set(value: Int): Unit = this.value = value

  def opCode: Int = value range (26,31)

  def src_1: Int = value range (21,25)

  def src_2: Int = value range (16,20)

  def dDst: Int = value range (11,15)

  def iDst: Int = value range (16,20)

  def aluCode: Int = value range (0,5)

  def immediate: Int = (value range (0,15)) extend 15

  def bOffset: Int = (value range (0,15)) extend 15

  def jOffset: Int = (value range (0,25)) extend 25

  def setWith(opCode: Int = 0, src_1: Int = 0, src_2: Int = 0, iDst: Int = 0, dDst: Int = 0, immediate: Int = 0, bOffset: Int = 0, jOffset: Int = 0, aluCode: Int = 0): Unit =
    value = (opCode << 26) | (src_1 << 21) | (src_2 << 16) | (iDst << 16) | (dDst << 11) | (immediate range (0,15)) | (bOffset range (0,15)) | (jOffset range (0,25)) | aluCode


  override def toString: String = f"$opCode $src_1 $src_2 $immediate"
}
