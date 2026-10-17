package opson.pages

import scala.swing._

object Actions {
  def switchWindow(
      targetWindow: Page,
      box: BoxPanel,
      frame: MainFrame,
      pages: MainPages
  ): Unit = {
    println(s"Switching to $targetWindow window...")
    box.contents.remove(box.contents.length - 1)

    targetWindow match {
      case Page.Settings =>
        println("Settings Page selected")
        box.contents += pages.settingsPage
      case Page.Main =>
        println("Main Page selected")
        box.contents += pages.mainPage
    }
    box.revalidate()
    frame.pack()
  }
}
