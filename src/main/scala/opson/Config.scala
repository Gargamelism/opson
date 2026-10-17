package opson

import com.typesafe.config.{ConfigFactory, ConfigValueFactory, Config as TsConfig}
import java.nio.file.{Files, Paths}
import org.json4s.{Formats, NoTypeHints}
import org.json4s.native.JsonMethods.parse
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import scala.deriving.Mirror
import org.json4s.{DefaultFormats, jvalue2extractable}

case class UserSettings(baseDir: String)

object Config {
  given formats: Formats = DefaultFormats.withHints(NoTypeHints)
  final val UserSettingsFile = "user-settings.json"
  private val config: TsConfig = {
    val defaultConfig = ConfigFactory.load()
    val userSettings = if Files.exists(Paths.get(UserSettingsFile)) then 
      loadUserSettings()
    else 
      createUserSettings(defaultConfig)

    userSettings.productElementNames.zip(userSettings.productIterator)
      .foldLeft(defaultConfig) { case (acc, (field, value)) =>
        acc.withValue(s"opson.$field", ConfigValueFactory.fromAnyRef(value))
      }
  }

  def setBaseDir(newBaseDir: String): Unit = {
    println(s"Base directory updated to: $newBaseDir")
    val suggestedBaseDir = Paths.get(newBaseDir.trim)
    if (!Files.exists(suggestedBaseDir) || !Files.isDirectory(suggestedBaseDir)) {
      throw new IllegalArgumentException(s"Invalid base directory: $newBaseDir")
    }
    ConfigFactory.invalidateCaches()
    setUserSettings("baseDir", suggestedBaseDir.toString)
  }

  def getBaseDir: String = {
    val homeDir = System.getProperty("user.home")
    Option(config.getString("opson.baseDir")).getOrElse(homeDir)
  }

  private def loadUserSettings(): UserSettings = {
    val jsonString = new String(Files.readString(Paths.get(UserSettingsFile)))
    parse(jsonString).extract[UserSettings]
  }

  private def createUserSettings(defaultConfig: TsConfig): UserSettings = {
    val userSettings = UserSettings(baseDir = System.getProperty("user.home"))
    saveUserSettings(userSettings)
    userSettings
  }

  private def setUserSettings(field: String, value: Any): Unit = {
    val userSettings = loadUserSettings()
    val updatedSettings = userSettings.productElementNames.zip(userSettings.productIterator).map {
      case (fieldName, _) if fieldName == field => value
      case (_, fieldValue)                        => fieldValue
    }
    val updatedUserSettings =
      summon[Mirror.ProductOf[UserSettings]].fromProduct(Tuple.fromArray(updatedSettings.toArray))
    saveUserSettings(updatedUserSettings)
  }

  private def saveUserSettings(userSettings: UserSettings): Unit = {
    val jsonString = write(userSettings)
    Files.writeString(Paths.get(UserSettingsFile), jsonString)
  }
}
