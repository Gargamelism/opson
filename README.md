# Opson
file categorizer

# Note
[Opson (Greek: ὄψον) and sitos (σίτος) are an important division in Ancient Greek foodways. Opson is the 'relish' that complements the sitos; sitos is the staple food part of the meal, i.e. grains like wheat or barley, and pulses like chickpeas and fava beans.](https://en.wikipedia.org/wiki/Opson_and_sitos)

# Plan
1. Complete GUI:
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