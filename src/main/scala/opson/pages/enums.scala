package opson.pages

enum PagesEnum {
  case Main, Settings
}

case class MainPages(mainPage: MainPage, settingsPage: SettingsPage)
