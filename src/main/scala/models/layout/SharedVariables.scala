package models.layout

import scalafx.stage.Stage
import views.architecture.{ArchitectureView, StateFul}

import scala.collection.mutable
import java.io.File

object SharedVariables {
  val views: scala.collection.mutable.Set[StateFul] = mutable.Set.empty
  var stage: Stage = _
  var defaultDir: File = _
  var architectureView: ArchitectureView = _
}
