package opson.pages

import scala.swing._
import javax.imageio.ImageIO

case class MainPages(mainPage: MainPage, settingsPage: SettingsPage)

class Home extends MainFrame { frame =>
  title = "Opson"
  preferredSize = new Dimension(800, 600)
  val pages = MainPages(new MainPage(), new SettingsPage())

  try {
    val iconUrl = Option(getClass.getResource("/opson_icon.png")).foreach(url => iconImage = ImageIO.read(url))
  } catch {
    case e: java.io.IOException =>
      println("Error loading icon: " + e.getMessage)
  }

  val mainContentBox = new BoxPanel(Orientation.Vertical) {
    contents += pages.settingsPage
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("Exit") { sys.exit(0) })
    }

    contents += new Menu("Window") {
      contents += new MenuItem(Action("Main") {
        Actions.switchWindow(
          Page.Main,
          mainContentBox,
          frame,
          pages
        )
      })

      contents += new MenuItem(Action("Settings") {
        Actions.switchWindow(
          Page.Settings,
          mainContentBox,
          frame,
          pages
        )
      })
    }
  }

  pack()
  visible = true
  contents = mainContentBox
}
