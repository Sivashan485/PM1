# team01-NotFalse-projekt2-texteditor

## Table of Documents
- [Klassenmodell]()
- [Testkonzept]()

# Texteditor 

Welcome to the texteditor created by !False

## Table of Contents
- [Getting Started](#getting-started)
- [Text Manipulation Tool](#text-manipulation-tool)
- [Editor Test](#editor-Test)
- [Compatibility](#compatibility)

## Getting Started
To run the Texteditor in Intellij, follow these steps:

1. **Clone the Repository**: Start by cloning this GitHub repository to your local machine using the following command:
   ```
   git clone https://github.zhaw.ch/PM1-IT23bZH-buga-mooi/team01-NotFalse-projekt2-text_editor.git
   ```
2. **Open in Intellij**: Open the project in your Intellij IDE.

3. **Navigate to the `module_text_editor` Module**:

4. **Navigate to the `TextEditorMain` Class**: Locate and open the `TextEditorMain.java` class.

5. **Run the Editor**: Right-click within the `TextEditorMain` class and choose the "Run" option to start the game.

6. **Edit Your Text**: Write some text in the editor. You can change the colour, the font and the size in the settings on top.

# Text Manipulation Tool

A simple command-line tool for manipulating and formatting text paragraphs. This tool allows users to perform various operations on text, such as adding, deleting, formatting paragraphs, generating indices, and more.

## Usage

### Commands

- `ADD [n]`: Add a new text.

- `DEL [n]`: Delete a paragraph. 

- `DUMMY [n]`: Insert pre-programmed placeholder text. 

- `EXIT`: Quit the program.

- `FORMAT RAW`: Set the output format to display paragraphs with prefixed paragraph numbers (default).

- `FORMAT FIX <b>`: Set the output format with a maximum column width of `b` characters. Line breaks occur only after spaces.

- `INDEX`: Generate an index of terms that appear more than three times across all paragraphs.

- `PRINT`: Display the text according to the current output format.

- `REPLACE`: Replace text. 

### Example

```bash
$ ./text-manipulation-tool
> ADD
Enter text for the new paragraph:
Lorem ipsum dolor sit amet, consectetur adipiscing elit.
> FORMAT FIX 20
> PRINT
1: Lorem ipsum dolor
   sit amet,
   consectetur
   adipiscing elit.
> EXIT
```

## Editor Test

# Compatibility
- [Overview](#overview)
- [System Requirements](#system-requirements)
- [Recommended Environment](#recommended-environment)
- [Getting Started](#getting-started)
- [Running in Other IDEs](#running-in-other-ides)

## Overview

The texteditor is concepted primarily for IntelliJ IDEA. While you are free to run the texteditor in other Integrated Development Environments (IDEs), it's important to note that doing so may result in graphical issues due to the inherent differences in the way various IDEs handle graphical rendering.

## System Requirements

To ensure a smooth experience, we recommend using IntelliJ IDEA as your primary IDE for running the editor. If you decide to use a different IDE, please be aware of the potential graphical issues that may arise, such as:

1. **UI Distortion:** Some graphical elements, including the user interface, may appear distorted or misaligned when viewed in IDEs other than IntelliJ IDEA.

2. **Performance Variability:** The performance may vary across different IDEs, potentially leading to lower frame rates or unexpected lag.

3. **Incompatibility:** Certain features and graphical effects that rely on IntelliJ IDEA-specific functionality may not work as intended or may be disabled when using other IDEs.

## Recommended Environment

For the best possible experience while using the texteditor, we strongly recommend the following environment:

 - **IDE:** IntelliJ IDEA
 - **Operating System:** [Compatible with your OS]
 - **Java Version:** [Recommended Java version, if applicable]
 - **Graphics Card:** [If applicable, mention any GPU recommendations]

## Getting Started

 1. Ensure you have IntelliJ IDEA installed on your system. You can download it from here. **[IntelliJ](https://www.jetbrains.com/idea/download/)**

 2. Open the project in IntelliJ IDEA and run the game from within the IDE to enjoy the optimal experience.
## Running in Other IDEs

If you choose to run the text-editor in a different IDE, please be aware of the potential graphical issues mentioned earlier. While we strive to provide a seamless experience, we cannot guarantee the same level of performance and graphical fidelity as when using IntelliJ IDEA.

We appreciate your understanding and will do our best to assist you with any problems you may encounter while editing text in different IDEs.

Thank you for choosing our texteditor created by **!False**, and we hope you enjoy your experience!


 
 
 
##### *Disclaimer: "IntelliJ IDEA" is a trademark of JetBrains s.r.o., which is not affiliated with the developers of this game. This readme is provided for informational purposes only and does not imply any endorsement or sponsorship by JetBrains s.r.o.*
