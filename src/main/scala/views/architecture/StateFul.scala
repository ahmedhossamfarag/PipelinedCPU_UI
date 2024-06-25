package views.architecture

import controllers.architecture.ArchitectureController

trait StateFul {
  def setState(architectureController: ArchitectureController): Unit
}
