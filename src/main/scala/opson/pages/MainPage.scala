package opson.pages

import scala.swing._
import opson.components.Header

class MainPage extends BoxPanel(Orientation.Vertical) {
  contents += new Header("Opson")
}
