package opson.components

import scala.swing._
import java.awt.Font

class Header(content: String) extends BoxPanel(Orientation.Horizontal) {
  contents += new Label(content) {
    font = new Font("Calibri", Font.BOLD, 16)
  }
}
