package opson.pages

import scala.swing._

object Actions {
  def switchWindow(
      targetWindow: PagesEnum,
      box: BoxPanel,
      frame: MainFrame,
      pages: MainPages
  ): Unit = {
    println(s"Switching to $targetWindow window...")
    val length = box.contents.length
    println(s"Current contents length: $length")
    box.contents.remove(box.contents.length - 1)

    targetWindow match {
      case PagesEnum.Settings =>
        println("Settings Page selected")
        box.contents += pages.settingsPage
      case PagesEnum.Main =>
        println("Main Page selected")
        box.contents += pages.mainPage
    }
    box.revalidate()
    frame.pack()
  }
}
