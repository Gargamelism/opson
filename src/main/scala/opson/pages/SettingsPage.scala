package opson.pages

import scala.swing._
import Swing._
import opson.components.Header
import opson.Config
import java.io.File
import scala.swing.event.ButtonClicked


class SettingsPage extends BoxPanel(Orientation.Vertical) { settingsPage =>
  contents += new Header("Settings")

  val baseDirButton = new Button("Select Base Directory")
  listenTo(baseDirButton)
  val baseDirTextField = new TextField { editable = true; text = Config.getBaseDir }
  val baseDirBox = new BoxPanel(Orientation.Horizontal) {
    contents += new Label("Base Directory: ")
    contents += baseDirTextField
    contents += baseDirButton
  }
  contents += baseDirBox

  val saveButton = new Button("Save Settings")
  listenTo(saveButton)
  contents += saveButton
  contents += new BorderPanel {
    add(saveButton, BorderPanel.Position.East)
  }

  reactions += {
    case ButtonClicked(`baseDirButton`) =>
      val chooser = new FileChooser(new File("."))
      chooser.title = "Select Base Directory"
      chooser.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly

      chooser.showOpenDialog(settingsPage) match {
        case FileChooser.Result.Approve =>
          val selectedDir = chooser.selectedFile
          println(s"Selected base directory: ${selectedDir.getAbsolutePath}")
          baseDirTextField.text = selectedDir.getAbsolutePath
        case FileChooser.Result.Cancel =>
          println("Directory selection canceled.")
        case FileChooser.Result.Error =>
          println("An unexpected error occurred during directory selection.")
      }
    case ButtonClicked(`saveButton`) =>
      try {
        Config.setBaseDir(baseDirTextField.text)
      } catch {
        case e: Exception =>
          println(s"Error saving settings: ${e.getMessage}")
          Dialog.showMessage(
            settingsPage,
            "Please select a valid directory.",
            title = "Invalid Directory",
            Dialog.Message.Error
          )
      }

  }
}
