package opson

object Main {
  def main(args: Array[String]): Unit = {
    val home = new pages.Home()
    home.visible = true
    println("main window is visible")
  }
}
