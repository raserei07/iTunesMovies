
# iTunesMovie
An android app that shows a list of Movies from the iTunes Search.

reference:
https://itunes.apple.com/search?term=star&country=au&media=movie

# UI and Design

![Screenshot_2022-09-06-13-36-36-376_com argoncillo itunesmovieapp](https://user-images.githubusercontent.com/40202220/188555878-dc2a3461-9b94-45ef-8f84-61b603599a70.jpg)

For the UI and Design, I went with a grid list in with 2 columbs on the Main screen, while another screen for displaying the description of an item.

# Architecture: MVVM with Clean & DI

![process-flow](https://user-images.githubusercontent.com/40202220/188776408-8052c9da-cff4-4ca8-aa74-623d5bc31ff0.png)

The architectural pattern of choice is **MVVM** with over other architecture patterns since android already adopted the use of ViewModel for handling data in the UI while transacting in the data source. And also include **Clean Architecture** which helps the app to be easily scaled to a large project, ease in testing implementation, and flexible when there are new requirement changes to the code. Since the app also uses clean architecture, the project uses android jetpack **Hilt** dependency injection was used.

### Repository Pattern
The architecture also leverages the use of **Repository Pattern** which acts as an intermediary between the domain model layers and data mapping. so the ViewModel just calls the Repository and it will handle scenarios like when the phone has no internet to fetch data from the network, it can instead use the local database for loading the data.  

Please see `MovieRepository.kt` for repository pattern reference.

### Coroutines

The app uses coroutines so the app dynamically update when there are changes observed in the data being reference. it is also cold and lazy which consumes little phone resources since it only emits data as flow when there is an observer subscribed. 

### Navigation

In this app, the android jetpack **Navigation** is used to handle navigation and data transactions between fragments. Because of this implementation, the app only uses one activity with multiple fragments.

### Persistence
For persistence, The app uses android jetpack **Room** which is widely adopted by the android community as a reliable library for handling local data storage.

The app stores the response `Movie` and also updates it for every successful network call inside `MovieRepository`. The stored data will be exposed first to the UI, then executes a network call which when successful, updates the stored data and exposes the result to the UI. Whenever the app goes offline, proper error handlers are used in order to properly display to the user.

