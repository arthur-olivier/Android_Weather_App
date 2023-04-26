# Android Weather App 

This is an Android weather application that utilizes the Room persistence library to incorporate an SQLite database to save locations for which the user requests a forecast. The app also features a navigation drawer that displays a list of previously requested cities, making it easier for the user to get forecasts for the cities that are most important to them.


# Prerequisites

Before running the application, you need to have the following:

* Android Studio IDE
* An OpenWeather API key. Add your API key in MainActivity.kt to make the app work.

# Getting Started

To get started, clone the repository and open the project using Android Studio. Then follow these steps:

1. Build the project by selecting Build > Make Project in the menu bar or by using the keyboard shortcut Ctrl+F9.
2. Run the app by selecting Run > Run 'app' or by using the keyboard shortcut Shift+F10.
3. Choose your preferred emulator or connect your Android device to your computer and wait for the app to be installed and launched.

# Features

This app has the following features:

1. <b>Viewing Weather Information</b> - The app displays weather information such as the temperature, the wind speed, and the weather condition in the user's preferred city.
2. <b>Saving Locations</b> - The app saves a representation of a city in an SQLite database whenever the user requests a forecast for a new city. The name of the city and a timestamp indicating when the user last viewed a forecast of this city are stored in the database.
3. <b>Navigation Drawer</b> - The app features a navigation drawer that displays a list of previously requested cities. The user can select a city from the list to view the weather information of that city.

# Implementation Details

The app is built using the following technologies and libraries:

* Kotlin and java programming language
* Room persistence library
* Retrofit HTTP client library
* Android Jetpack Navigation component
* OpenWeather API
The following are the tasks accomplished in the development of this app:

1. Used the Room persistence library to incorporate an SQLite database into the app. The database stores the name of a city and a timestamp indicating when the user last viewed a forecast of this city.
2. Modified the app so that whenever the user changes the forecast city in the application settings, the new city is saved into the database. The app avoids storing duplicate cities in the database.
3. Migrated the app to use the Android Jetpack Navigation component to manage navigation between screens. The navigation drawer is implemented using the Navigation component.

# Conclusion

This Android weather app showcases the use of the Room persistence library and the Android Jetpack Navigation component to enhance the user experience. The app can be further improved by adding more weather information and additional features such as a search function for finding cities.
