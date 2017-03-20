## Android companion app for Food Inventory Tracker (FIT)

This repository contains the Android companion app for FIT, the ECE 492 capstone project for Group 2, University of Alberta. This app is supposed to improve the user experience of interfacing with the system, by providing features that are not feasible on the DE2 board, such as inventory management and recipe search and discovery.


### Building and Running
This is a standard Android Studio project; so to compile and run the app, download and install [Android Studio IDE](https://developer.android.com/studio/index.html). There probably are other command line options available, but using the IDE is the easiest route.

When you first open the project, Android Studio will install all the Gradle dependencies for the app. You should be able to compile and run the app on an actual device/emulator from within the UI.

### Code organization
The general MVC architecture of the app is as follows. The "X" is a placeholder for the major action a screen does:

![androidclientumlclassdiagram](https://cloud.githubusercontent.com/assets/4692593/22669259/09b40b0e-ec81-11e6-9aca-1d950663d9e7.png)

`aaku492.smartfoodtracker`

* `App.java`: This is the main application entry point. It initializes all the resources needed by the app, such as the `DataProvider`.
* `MainActivity.java`: This is the main activity that is started when the app loads. It does not have a UI; it just checks the state of the app, and moves on to the appropriate initial screen.
* `FragmentContainerActivity.java`: The super class for all parent container activities for all screens.
* `FITFragment.java`: The super class for all fragments used in the app.
* `FragmentInitInfo.java`: A collection of `FITFragment` information needed to properly initialize a fragment.
* `.common`: Contains all the utility code shared among multiple screens.
* `.inventory`: Contains the code for the different inventory related screens.
* `.recipes`: Contains the code for the different recipes related screens.

