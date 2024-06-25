package controllers.layout

import base.Runner
import controllers.architecture.ArchitectureController
import models.architecture.Architecture
import models.layout.SharedVariables

class RunController(var architectureController:ArchitectureController) {

  var runner = new Runner(architectureController)

  def step(): Boolean = {
    if(!runner.finish)
      runner.stepThen()(adaptState)
    !runner.finish
  }

  def proceed(): Boolean = {
    runner.proceedThen()(adaptState)
    false
  }

  def adaptState(): Unit = SharedVariables.views.foreach(_.setState(architectureController))

  def getArchitecture: Architecture = architectureController.arch

  def reset(): Unit = {
    architectureController = new ArchitectureController(new Architecture(architectureController.arch.insMemory.data, architectureController.arch.memory.cleanData))
    runner = new Runner(architectureController)
    adaptState()
  }
}
