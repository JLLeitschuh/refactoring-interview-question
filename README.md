# Refactoring Interview Problem

The goal of this problem is to try to see how you tackle refactoring code from one project to another.

## Project Goals

The goal of this problem is to refactor the logic in the `monolith` project
so that it resides in the `library` project.
There are a few pre-written tests in the `library` project that should be
re-enabled and made to pass.

### Constraints

 - You are not allowed to change any code inside of the `external` sub-project.
 - The `monolith` project must depend upon the `library` project and the `external` project.
   The `library` project can not depend upon the `external` project.
   (The project is already configured this way, if you don't touch the build files you should be fine.)
 - You should not need to change the `AlgorithmEngineTest` but you are free to change the
   `AlgorithmEngine` as much as you wish.

## Hints

 - Are there methods that are instance methods that could be made `static`?
   - If a method has a dependency on a field, could that field be passed as an argument instead?
 - Are there methods that can be made `private`?
 - Are there fields in classes that are not being used?
 - In general, constructors should "do no work". Instead, try to make it so that constructors are
 simply assigning their parameters to fields.
 - In IntelliJ pressing `F6` when a class is selected in the project view will pull up a UI to help
 moving classes. This can also be used to move static methods to another class.
 - Look for TODOs in the source code for additional pointers.

## Environment Setup

You will need:
 - JDK 8 installed on your machine with the `$JAVA_HOME` environment variable set correctly.
 - A recent version of IntelliJ IDE Community Edition.

### Configure [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/)

In order to setup IntelliJ for development use the following steps:

1. If you already have a project open use `File -> Open` or just select `Open` from the welcome screen.
2. Select the [settings.gradle](settings.gradle) file and click `Open`.
3. When prompted:
    1. Un-check the `Create seperate module per source set` option.
    2. Check the `Use auto-import` option.
    3. Make sure the `Gradle JVM` option is set to `1.8`.
4. Wait for the project to load.
5. Update your Kotlin Plugin:
    1. Open the Kotlin Plugin Updates window using `⌘ + Shift + A` and typing "kotlin plugin updates".
    2. Ensure `Stable` is selected and press `Check for updates`.
    3. Update and restart IntelliJ when prompted (if necessary).

## Testing Before Submission

Before you submit this project make sure you run `./gradlew check` this will ensure that your
submission passes all tests and conforms to the style formatter.

### Reformatting Your Code Before Submission

To reformat your code quickly simply run `./gradlew spotlessApply`.
