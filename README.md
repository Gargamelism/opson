# Opson
file categorizer

# Note
[Opson (Greek: ὄψον) and sitos (σίτος) are an important division in Ancient Greek foodways. Opson is the 'relish' that complements the sitos; sitos is the staple food part of the meal, i.e. grains like wheat or barley, and pulses like chickpeas and fava beans.](https://en.wikipedia.org/wiki/Opson_and_sitos)

# Setup (macOS)

Opson is a Scala 3 desktop app (Scala Swing) built with sbt. To get a dev environment running:

1. **Install a JDK** (sbt and Scala need one; JDK 17+ recommended):
   ```sh
   brew install openjdk@17
   ```
   Then make sure it's on your `PATH` (follow the `brew info openjdk@17` instructions), or manage it with a tool like [sdkman](https://sdkman.io/) / [coursier](https://get-coursier.io/).

2. **Install sbt**:
   ```sh
   brew install sbt
   ```
   This project pins `sbt.version=2.0.4` in `project/build.properties` — running `sbt` in the project directory will automatically fetch that exact version the first time, so you don't need to match it manually.

3. **Clone the repo**:
   ```sh
   git clone git@github.com:Gargamelism/opson.git
   cd opson
   ```

4. **Run the app**:
   ```sh
   sbt run
   ```
   This compiles the project and opens the app's main window.

5. **Run the tests**:
   ```sh
   sbt test
   ```

## Optional: editor setup

The repo includes Metals/BSP config (`project/metals.sbt`, `.bsp/sbt.json`), so it works out of the box with [Metals](https://scalameta.org/metals/) in VS Code (or any BSP-compatible editor):

```sh
brew install --cask visual-studio-code
```
Then install the "Scala (Metals)" extension from the VS Code marketplace and open the project folder — Metals will pick up the existing BSP configuration automatically.

# Plan
1. Complete GUI:
   1. UI Layout mechanism
   1. Config page:
      1. Add base folder
      1. Choose AI provider
      1. Add AI key
      1. Autosave?
   1. Main page:
      1. Categorize file choice
      1. Add drag-n-drop file
   1. Add logic that if config is not set open it, otherwise - main page
   1. Add approval pop up before saving
   1. Add status pop up after save
1. Choose config tech (file/sqlite etc.)
1. Setup claude integration
1. Add flow:
   1. Read file, send text/file to claude with prompt, (including current category options)
   1. Prompt user
   1. Save file
   1. Update user