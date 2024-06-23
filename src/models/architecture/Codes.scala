package models.architecture

object Codes {

  sealed trait OpCode{
    def asString: String
    def code: Int
  }

  object OpCode{
    object LW   extends OpCode{val asString = "lw";   val code = 1}
    object SW   extends OpCode{val asString = "sw";   val code = 2}
    object ADDI extends OpCode{val asString = "addi"; val code = 3}
    object BEQ  extends OpCode{val asString = "beq";  val code = 4}
    object BNE  extends OpCode{val asString = "bne";  val code = 5}
    object J    extends OpCode{val asString = "j";    val code = 6}
    object ALU  extends OpCode{val asString = "alu";  val code = 7}

    val values = List(LW , SW , ADDI , BEQ , BNE , J , ALU)

    def fromCode(code: Int): Option[OpCode] = values.find(_.code == code)

    def fromString(str: String): Option[OpCode] = values.find(_.asString == str)
  }

  sealed trait ALUCode {
    def asString: String
    def code: Int
  }

  object ALUCode {
    object ADD extends ALUCode{val asString = "add"; val code = 1}
    object SUB extends ALUCode{val asString = "sub"; val code = 2}
    object AND extends ALUCode{val asString = "and"; val code = 3}
    object NOR extends ALUCode{val asString = "nor"; val code = 4}
    object SLT extends ALUCode{val asString = "slt"; val code = 5}
    object SLTU extends ALUCode{val asString = "sltu"; val code = 6}

    val values = List(ADD , SUB , AND , NOR , SLT , SLTU)

    def fromCode(code: Int): Option[ALUCode] = values.find(_.code == code)

    def fromString(str: String): Option[ALUCode] = values.find(_.asString == str)
  }

  sealed trait Register {
    def asString: String
    def code: Int
  }

  object Register {
    object AX extends Register{val asString = "ax"; val code = 1}
    object BX extends Register{val asString = "bx"; val code = 2}
    object CX extends Register{val asString = "cx"; val code = 3}
    object DX extends Register{val asString = "dx"; val code = 4}
    object SP extends Register{val asString = "sp"; val code = 5}
    object BP extends Register{val asString = "bp"; val code = 6}
    object CS extends Register{val asString = "cs"; val code = 7}
    object DS extends Register{val asString = "ds"; val code = 8}
    object ES extends Register{val asString = "es"; val code = 9}

    private val values = List(AX , BX , CX , DX , SP , BP , CS , DS , ES)

    def count: Int = values.length

    def fromCode(code: Int): Option[Register] = values.find(_.code == code)

    def fromString(str: String): Option[Register] = values.find(_.asString == str)

    def indexOf(register: Register): Option[Int] = values.zipWithIndex.find(_._1 == register).map(_._2)

    def atIndex(i: Int): Register = values(i)
  }

}
