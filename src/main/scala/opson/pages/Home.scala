package opson.pages

import scala.swing._
import scala.swing.event.ButtonClicked

class Home extends MainFrame { frame =>
  title = "Opson"
  preferredSize = new Dimension(800, 600)
  
  contents = new BoxPanel(Orientation.Vertical) { box =>
    contents += new Label("Welcome to Opson!")
    contents += new CheckBox("Show Settings Page") { checkBox => 
      listenTo(this)

      val settingsPage = new SettingsPage()
      val mainPage = new MainPage()
      reactions += {
        case event: ButtonClicked =>
          val length: Long = box.contents.length
          println(s"Current contents length: $length")
          if box.contents.contains(settingsPage) then {
            box.contents.remove(box.contents.length - 1)
          }
          if box.contents.contains(mainPage) then {
            box.contents.remove(box.contents.length - 1)
          }
          
          selected match {
            case true => 
              println("Settings Page selected")
              box.contents += settingsPage
            case false => 
              println("Main Page selected")
              box.contents += mainPage
          }
          box.revalidate()
          frame.pack()
      }
    }
  }
}