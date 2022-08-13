# PasswordGenerator
> A.K.A obfuscatedGenerator

## Users

### Setup

1. `git clone https://github.com/obfuscatedgenerated/PasswordGenerator.git`
2. `java -jar PasswordGenerator v(version).jar` or double-click the JAR.

### Usage

You can generate a new password with the "Generate" button.

You can copy the password to our clipboard with the "Copy" button.

You can set the length of the password with the length input box and specify the make-up of the password using the checkboxes.

You can activate XKCD mode for [XKCD-style passwords](https://xkcd.com/936/).

<!-- 
UNCOMMENT WHEN FEATURE IS RELEASED

You can set a custom delimiter per characters using the delimiter options.

-->


## Collaborators

### Setup

1. `git clone https://github.com/obfuscatedgenerated/PasswordGenerator.git` or make a fork and clone that instead.
2. `mvn install`
3. [Download the Apache Pivot JAR files](https://dlcdn.apache.org//pivot/binaries/apache-pivot-2.0.5.zip) for syntax highlighting for your IDE. The /deps/ directory has been gitignored for this purpose.
4. The MainWindow class holds the code for initialising the program and intermediary code between classes and the UI. The RandomGenerator class holds the code for random character password generation. The XKCDGenerator class holds the code for generating XKCD passwords. The UI is based on PassGen.bxml in resources.
