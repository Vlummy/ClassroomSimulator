![GitHub](https://img.shields.io/github/license/Vlummy/FXToolbox.svg)
[![Build Status](https://travis-ci.org/Vlummy/FXToolbox.svg?branch=master)](https://travis-ci.org/Vlummy/FXToolbox)
![GitHub release](https://img.shields.io/github/release-pre/Vlummy/FXToolbox.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Vlummy/FXToolbox.svg)


# FXToolbox

#### A toolbox for making certain operations like scene switching, adding animations and using templates simpler.

Classes:
Window (A custom window for javafx application)
Router (A router for custom Window)
SceneSwitcher (Switching scenes)

##### Example using Window class:
```java
new Window(primaryStage)
    .bind("login", "fxml/login.fxml")
    .bind("date", new DatePicker())
    .setView("login")
    .setTitle("Custom Window")
    .setHeaderStyle("-fx-background-color: lightblue;")
    .addCSSFile("styles/customStyle.css") // #window id is reserved for the main window.
    .addBorderRadius(12.0)
    .show();

##### Example using Window with Router to easily switch view:

```java
Router.connect(new Window(primaryStage)
        .bind("login", "fxml/login.fxml")
        .bind("system", new SystemView())
        .setView("login")
        .setTitle("Custom Window")
        .setHeaderStyle("-fx-background-color: lightblue;")
        .show()
);
```
##### Switching a view with Router:
```java
logInButton.setOnAction(event -> Router.switchTo("system"));
```
Example using SceneSwitcher class:
```java
SceneSwitcher.getInstance("SceneOne", "Application Title")
        .applySwitchable(primaryStage)
        .bindDirectory("fxml", new String[] {"SceneOne", "SceneTwo"})
        .show();
```
SceneSwitcher is used to easily change view of the scene, but only on Stage. If you choose to use Window class, use Router class.
Router holds a SceneSwitcher, but not all methods are usable since Window is not actually a Stage. Its only a BorderPane layout made
to mimic a stage.

##### Control the view of scene:

Preferred setup is to make the application switchable and bind all fxml documents, or classes if you don't use fxml
to keys. Example setup:
```java
SceneSwitcher.getInstance("SceneOne") // the first view to be loaded when application starts (Optional)
        .applySwitchable(primaryStage) // Make application switchable
        .bindDirectory("fxml", new String[] {"SceneOne", "SceneTwo"}) // Bind fxml directory in resources folder to array of keys
        .show(); // Show the stage
```
##### Bind multiple views
```java
SceneSwitcher.getInstance("SceneOne")
        .applySwitchable(primaryStage)
        .bindListOfViews(
                new Parent[] {new BorderPane(), new Login(), new GridPane()},
                new String[] {"BorderPane", "Login", "GridPane"})
        .bindDirectory(
                "fxml",
                new String[] {"SceneOne", "SceneTwo"})
        .show();
```
##### Bind in sequence:
```java
SceneSwitcher.getInstance("Login", "Application Title")
        .applySwitchable(primaryStage)
        .bind("SceneOne", "fxml/scene_one.fxml")
        .bind("SceneTwo", "fxml/scene_two.fxml")
        .bind("BorderPaneOne", new BorderPane())
        .bind("Login", new Login())
        .show();
```
##### How to switch scenes when application is switchable:
```java
switchButton.setOnAction(event -> SceneSwitcher
        .getInstance()
        .changeToScene("SceneTwo")); // The key that points to the requested layout
```

##### Applying scene switching to buttons if there is no key, value bindings:
```java
button1.setOnAction(event -> {
    SceneSwitcher sceneSwitcher = SceneSwitcher.getInstance();
    sceneSwitcher.changeToScene(event, "fxml/scene_two.fxml"); // Relative path to resources folder
});
```

##### Example using Animations:
```java
myButton.setOnAction(event -> Router.switchTo(
        "login", // Current view
        "main", // Requested view
        new FadeOut(0.1), // Animation on current view
        new FadeIn(0.1) // Animation on requested view
        )
);
```
To create custom animations, implement the Animatable interface to you class.

#### Font
To add font TTF files add a custom css file which is located in the resources folder.
add the font
````css
@font-face {
    src: url("Livvic-Regular.ttf");
}

.root {
    -fx-font-family: 'Livvic Regular', sans-serif;
}
````

Remeber to put the .ttf file in the same folder as the css file or use a relative path.
The above font filename is just an example.