package opson

import com.typesafe.config.{ConfigFactory, ConfigValueFactory, Config}
import java.nio.file.{Files, Paths}
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import scala.compiletime.constValue
import scala.deriving.Mirror
implicit val formats: Formats = Serialization.formats(NoTypeHints)

case class UserSettings(baseDir: String)

object Config {
  val USER_SETTINGS_FILE: String = constValue["user-settings.json"]
  private val config: Config = {
    val defaultConfig = ConfigFactory.load()
    val userSettings = Files.exists(Paths.get(USER_SETTINGS_FILE)) match {
      case true  => loadUserSettings()
      case false => createUserSettings(defaultConfig)
    }

    val config = userSettings.productElementNames.toList
      .foldLeft(defaultConfig) { (acc, field) =>
        acc.withValue(s"opson.$field", ConfigValueFactory.fromAnyRef(userSettings.productElement(0).toString))
      }

    config
  }

  def setBaseDir(newBaseDir: String): Unit = {
    println(s"Base directory updated to: $newBaseDir")
    val suggestedBaseDir = newBaseDir.trim
    if (Files.exists(Paths.get(suggestedBaseDir)) && Files.isDirectory(Paths.get(suggestedBaseDir))) {
      config.withValue("opson.baseDir", ConfigValueFactory.fromAnyRef(suggestedBaseDir))
      ConfigFactory.invalidateCaches()
      setUserSettings("baseDir", suggestedBaseDir)
    }
  }

  def getBaseDir: String = {
    val homeDir = System.getProperty("user.home")
    val baseDir = Option(config.getString("opson.baseDir")).getOrElse(homeDir)
    baseDir
  }

  private def loadUserSettings(): UserSettings = {
    val jsonString = new String(Files.readAllBytes(Paths.get(USER_SETTINGS_FILE)))
    parse(jsonString).extract[UserSettings]
  }

  private def createUserSettings(defaultConfig: Config): UserSettings = {
    val userSettings = UserSettings(baseDir = System.getProperty("user.home"))
    saveUserSettings(userSettings)
    userSettings
  }

  private def setUserSettings(field: String, value: Any): Unit = {
    val userSettings = loadUserSettings()
    val updatedSettings = userSettings.productElementNames.zip(userSettings.productIterator).map {
      case (fieldName, _) if (fieldName == field) => value
      case (_, fieldValue)                        => fieldValue
    }
    val updatedUserSettings =
      summon[Mirror.ProductOf[UserSettings]].fromProduct(Tuple.fromArray(updatedSettings.toArray))
    saveUserSettings(updatedUserSettings)
  }

  private def saveUserSettings(userSettings: UserSettings): Unit = {
    val jsonString = write(userSettings)
    Files.write(Paths.get(USER_SETTINGS_FILE), jsonString.getBytes)
  }
}
