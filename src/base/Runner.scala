package base

import controllers.architecture.ArchitectureController

class Runner(val architectureController: ArchitectureController){

  def finish: Boolean = architectureController.arch.pc/4 == architectureController.arch.insMemory.size

  def step(show: Boolean = false): Unit = {
    architectureController.performCycle

    if(show){
      println(f"pc = ${architectureController.arch.pc}")
      println(f"registers: ${architectureController.arch.registers}")
      println(f"memory: ${architectureController.arch.memory}")
      println(".".repeat(100))
    }
  }

  def stepThen(show: Boolean = false)(after: () => Unit): Unit = {
    step(show)
    after()
  }

  def proceed(show: Boolean = false): Unit = while(!finish) step(show)

  def proceedThen(show: Boolean = false)(after: () => Unit): Unit = {
    proceed(show)
    after()
  }

  def proceedThenEach(show: Boolean = false)(after: () => Unit): Unit = {
    while(!finish) stepThen(show)(after)
  }


}
