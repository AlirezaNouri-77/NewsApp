News App

Features
Fetch and display news articles using Ktor for network requests.
Cache articles locally with Room for offline access.
Utilize Jetpack Compose for the modern UI.
Manual dependency injection for better control.

Architecture - MVI Pattern
I tried to implement MVI pattern

The app adheres to the Model-View-Intent (MVI) pattern, ensuring clear separation of concerns:
Model: Houses data, business logic, and interactions with data sources.
View: Builds the user interface with Jetpack Compose.
Intent: Represents user actions or events that trigger changes in the model and update the view.

Setup and Usage
Clone this repository.
Obtain an API key from newsapp.io.
Insert your API key following the path: com/example/newsapp/constant/constant.kt
Open the project in Android Studio.
Build and run the app on an Android emulator or device.
