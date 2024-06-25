package views.layout

import controllers.layout.MainController
import javafx.scene.control.Tooltip
import models.layout.SharedVariables
import scalafx.application.Platform
import scalafx.scene.Cursor
import scalafx.scene.control.Button
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polyline
import scalafxml.core.macros.sfxml

import java.util.{Timer, TimerTask}

@sfxml
class MainView(
                val stepShape: Polyline,
                val proceedShape: Polyline,
                val proceedFixedRateShape: Polyline,
                val resetShape: Polyline,
                val stepButton: Button,
                val proceedButton: Button,
                val proceedFixedRateButton: Button,
                val resetButton: Button,
                val newButton: Button
                    ) {

  private case class Active(var forward: Boolean = false, var backward: Boolean = false, var fixedRate: Boolean = false)

  private val mainController = new MainController()
  private val active: Active = Active()
  private var timer: Option[Timer] = None
  private var fixedRateTask: Option[TimerTask] = None

  stepButton.setTooltip(new Tooltip("Step Forward"))
  proceedButton.setTooltip(new Tooltip("Proceed Forward To End"))
  proceedFixedRateButton.setTooltip(new Tooltip("Proceed Forward With Fixed Rate"))
  resetButton.setTooltip(new Tooltip("Reset"))


  setActive(false)

  stepButton.setOnMouseClicked{ _ =>
    if(active.forward) setActiveForward(mainController.step())
  }

  proceedButton.setOnMouseClicked{ _ =>
    if(active.forward)  setActiveForward(mainController.proceed())
  }

  proceedFixedRateButton.setOnMouseClicked { _ =>
    if(fixedRateTask.isDefined){
      endFixedRate()
      setActiveFixedRate(flag = true)
    }else if(active.forward){
      mainController.getFixedRatePeriod.foreach{ p =>
        val timer_ = new Timer()
        val task = new TimerTask {
          override def run(): Unit = Platform.runLater(
            if (!mainController.step()) {
              endFixedRate()
              setActiveForward(false)
            }
          )
        }
        timer_.schedule(task, p, p)
        fixedRateTask = Some(task)
        timer = Some(timer_)
        setActiveFixedRate(flag = false, handWithFalse = true)
      }
    }
  }

  resetButton.setOnMouseClicked{ _ =>
    if(active.backward)  {
      endFixedRate()
      mainController.reset()
      setActive(true)
    }
  }

  newButton.setOnMouseClicked { _ =>
    if(mainController.newArchitecture()) {
      endFixedRate()
      setActive(true)
    }

  }

  private def setActive(flag: Boolean):Unit = {
    setActiveForward(flag)
    setActiveBackward(flag)
    setActiveFixedRate(flag)
  }

  private def setActiveForward(flag: Boolean):Unit = {
    val color = if(flag) Color.Green else Color.Red
    val cursor = if(flag) Cursor.Hand else Cursor.Default
    stepShape.fill = color
    stepButton.cursor = cursor
    proceedShape.fill = color
    proceedButton.cursor = cursor
    active.forward = flag
    if(!flag) setActiveFixedRate(flag = false)
  }

  private def setActiveBackward(flag: Boolean):Unit = {
    val color = if(flag) Color.Green else Color.Red
    val cursor = if(flag) Cursor.Hand else Cursor.Default
    resetShape.fill = color
    resetButton.cursor = cursor
    active.backward = flag
  }

  private def setActiveFixedRate(flag: Boolean, handWithFalse: Boolean = false):Unit = {
    val color = if(flag) Color.Green else Color.Red
    val cursor = if(flag ) Cursor.Hand else if(handWithFalse) Cursor.Hand else Cursor.Default
    proceedFixedRateShape.fill = color
    proceedFixedRateButton.cursor = cursor
    active.fixedRate = flag
  }

  private def endFixedRate(): Unit = {
    fixedRateTask.foreach(_.cancel())
    timer.foreach(_.cancel())
    fixedRateTask = None
  }

  SharedVariables.architectureView.dataMemoView.setOnMouseClicked(_ => mainController.viewDataMemoryContent())
  SharedVariables.architectureView.instMemoView.setOnMouseClicked(_ => mainController.viewInstMemoryContent())
  SharedVariables.architectureView.registersView.setOnMouseClicked(_ => mainController.viewRegContent())


}