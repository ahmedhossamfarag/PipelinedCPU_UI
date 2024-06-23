package base

import models.architecture.Codes.{ALUCode, OpCode, Register}
import models.architecture.Instruction

import java.security.InvalidParameterException

object Encoder {
    class InstSplits(var label: Option[String] = None, var opCode: String = "", var args: Array[String] = Array.empty){
      def get(i: Int): String = if(i < args.length) args(i) else throw new Exception("Not Enough args")
    }

    def splitInst(line: String): InstSplits = {
      var inst = line.trim.toLowerCase
      val splits = new InstSplits()
      val labelEnd = inst.indexOf(":")
      if(labelEnd != -1){
        splits.label = Some(inst.substring(0, labelEnd))
        inst = inst.substring(labelEnd+1).trim
      }
      val opCodeEnd = inst.indexOf(" ")
      if(opCodeEnd != -1){
        splits.opCode = inst.substring(0, opCodeEnd)
        splits.args = inst.substring(opCodeEnd).split(",").map(_.trim)
      }else{
        splits.opCode = inst
      }
      splits
    }

   private def getLabels(instSplits: Array[InstSplits]): Map[String, Int] = {
     var map = Map[String,Int]()
     instSplits.zipWithIndex.foreach{s => if(s._1.label.isDefined){map = map.updated(s._1.label.get, s._2)}}
     map
   }

  def getRg(rg: String): Int =
    Register.fromString(if(rg.startsWith("$")) rg.substring(1) else rg)
      .map(_.code)
      .fold({throw  new Exception(f"Unknown Register $rg")})(c => c)

  private def getInt(s: String): Int =
    s.toIntOption.fold({throw new InvalidParameterException(f"Invalid Int $s")}){i => i}

  def getOffset(s: String)(line: Int)(labels: Map[String, Int]): Int = {
    try{
      getInt(s)
    }catch {
      case _: InvalidParameterException =>
        labels.get(s)
          .fold({throw new Exception(f"Unknown Label $s")})(l => l - line - 1)
      case e: Exception => throw e
    }
  }

  def getAddress(s: String): (Int, Int) = {
    val openB = s.indexOf("(")
    val closeB = s.indexOf(")")
    if(openB > -1 && closeB == s.length-1){
      val offset = s.substring(0, openB)
      val rg = s.substring(openB+1, closeB)
      return (getInt(offset), getRg(rg))
    }
    throw new Exception(f"Invalid Memory Address $s")
  }

  def encode(splits: InstSplits, line: Int)(labels: Map[String, Int]): Int = {
    val inst = new Instruction()
    splits.opCode match {
      case x if ALUCode.fromString(x).isDefined => inst.setWith(
        opCode = OpCode.ALU.code,
        dDst = getRg(splits.get(0)),
        src_1 = getRg(splits.get(1)),
        src_2 = getRg(splits.get(2)),
        aluCode = ALUCode.fromString(x).get.code
      )
      case OpCode.ADDI.asString => inst.setWith(
        opCode = OpCode.ADDI.code,
        iDst = getRg(splits.get(0)),
        src_1 = getRg(splits.get(1)),
        immediate = getInt(splits.get(2))
      )
      case OpCode.BEQ.asString | OpCode.BNE.asString => inst.setWith(
        opCode = OpCode.fromString(splits.opCode).get.code,
        src_1 = getRg(splits.get(0)),
        src_2 = getRg(splits.get(1)),
        bOffset = getOffset(splits.get(2))(line)(labels)
      )
      case OpCode.J.asString => inst.setWith(
        opCode = OpCode.J.code,
        jOffset = getOffset(splits.get(0))(line)(labels)
      )
      case OpCode.LW.asString | OpCode.SW.asString =>
        val address = getAddress(splits.get(1))
        inst.setWith(
          opCode = OpCode.fromString(splits.opCode).get.code,
          iDst = getRg(splits.get(0)),
          src_1 = address._2,
          immediate = address._1
        )
      case "noop" =>
      case _ => throw new Exception(f"Unknown OpCode ${splits.opCode}")
    }

    inst.get
  }

  def encode(lines: Array[String]): Array[Int] = {
    val splitsArray = lines.map(splitInst)
    val labels = getLabels(splitsArray)
    splitsArray.zipWithIndex.map(
      l => try encode(l._1, l._2)(labels) catch {
      case e:Exception => throw new Exception(f"Line ${l._2} => ${e.getMessage}")
    })
  }

  def encodeData(data: Array[String]): Array[Int] =
  data.zipWithIndex.map(
      l => try Integer.parseInt(l._1) catch {
        case e:Exception => throw new Exception(f"Line ${l._2} => ${e.getMessage}")
      })
}
