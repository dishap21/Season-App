# Season-App

![App Logo](splashscreen.jpg)

## Overview
The Season App is a user-friendly Android application designed for food enthusiasts, offering a convenient platform to access a vast collection of recipes from around the world. This app aims to streamline the process of finding and exploring diverse food recipes, eliminating the need for traditional methods such as index cards or paper-based cookbooks.

## Purpose
The primary purpose of this project is to create an intuitive and time-saving app for users passionate about cooking. By providing a diverse range of recipes, nutrition details, and a user-friendly interface, the app aims to elevate users' culinary skills while simplifying the cooking process.

## Objective
- Develop a user-friendly application for accessing recipes globally.
- Enable users to search recipes based on ingredients, cuisine, or meal type.
- Save user time by allowing them to search recipes using existing ingredients.
- Provide a feature to save and organize favorite recipes for future use.
- Display basic nutritional information, including protein and carbs.
- Create a visually appealing and enjoyable user interface for an enhanced user experience.

## Applicability
The Recipe Explorer App is compatible with any Android device.

## Technologies Used
### 1. Programming Languages
#### Java
Java is the primary programming language used for Android app development. It offers versatility and platform independence, allowing the app to run on various devices without recompilation.
**Java Version Used:** 17.0.2

### 2. Development Environment
#### Android Studio
Android Studio serves as the official integrated development environment (IDE) for Android app development. It provides tools for designing, testing, debugging, and publishing Android applications.
**Android Studio Version Used:** Bumblebee

### 3. Backend and Database
#### Firebase
Firebase is utilized for backend services, including authentication and real-time database functionalities. It facilitates seamless user authentication and data synchronization across devices.

### 4. API
#### Spoonacular API
The Spoonacular API is employed to retrieve extensive information about recipes, ingredients, and food products. It allows users to search for recipes using natural language and provides valuable data for the app.

### 5. Libraries
#### Volley
Volley is an HTTP library that simplifies networking in Android apps. It manages network requests, caches data, and enhances the overall efficiency of the application.
#### Picasso
Picasso is used for hassle-free image loading within the app, ensuring a smooth user experience. It supports complex image transformations with minimal memory usage.
#### Gson
Gson facilitates the conversion of Java Objects into JSON representation and vice versa. It is employed for efficient data handling within the app.
#### RecyclerView Decorator
RecyclerView Decorator is a utility class used to enhance the visual appearance of RecyclerView items during swiping actions.

## Getting Started

To run the Recipe Explorer App, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Obtain your Spoonacular API key from [Spoonacular](https://spoonacular.com/food-api) and replace the placeholder in the code.
4. Build and run the app on an Android device or emulator.

**Note:** Ensure that you have the required API keys for Spoonacular to access recipe information.

## Application Design

The app is structured into different packages for clarity and organization:

- **Activities:** Contains all the activities in the app, each serving a specific role.
- **Fragment:** The main activity is divided into three fragments, each displaying relevant content.
- **Models:** Stores getter, setter, and constructor methods, making data access more convenient.
- **RecyclerView:** Includes RecyclerView Adapters for efficient handling of list views.

### Screenshots

![Screenshot 1](link_to_screenshot1)
![Screenshot 2](link_to_screenshot2)

Feel free to explore the code and contribute to enhancing the Recipe Explorer App!