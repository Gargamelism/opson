package opson.pages

import scala.swing._
import opson.components.Header
import java.io.File
import scala.swing.event.ButtonClicked

class SettingsPage extends BoxPanel(Orientation.Vertical) { settingsPage =>
  contents += new Header("Settings")

  val baseDirButton = new Button("Select Base Directory")
  contents += baseDirButton

  listenTo(baseDirButton)

  reactions += { case ButtonClicked(`baseDirButton`) =>
    val chooser = new FileChooser(new File("."))
    chooser.title = "Select Base Directory"
    chooser.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly

    chooser.showOpenDialog(settingsPage) match {
      case FileChooser.Result.Approve =>
        val selectedDir = chooser.selectedFile
        println(s"Selected base directory: ${selectedDir.getAbsolutePath}")
      case FileChooser.Result.Cancel =>
        println("Directory selection canceled.")
      case FileChooser.Result.Error =>
        println("An unexpected error occurred during directory selection.")
    }
  }
}
