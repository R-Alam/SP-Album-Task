# SP-Album-Task
Scottish power

# Description
* This is a simple application which retrives a list of Album titles from a remote data source , saves in local data source and displayes the sorted list based on the titles on the UI. 

# Libraries used
*	Retrofit - Retrofit as a REST Client library 
*	Gson - Gson converter as a Retrofit response mapping library
* Hilt - Dependency injection

# Android components
*	RecyclerView - Used to list the Albums.
*	Constraint layout - Simple, flat hierarchies in a layout.
*	Coroutines – For Asynchronous or non-blocking programming
*	Data binding – To bind the data with the UI and to improve the performance, prevent memory leaks and null pointer exceptions.

# Android Architecture Components (Android Jetpack)
*	ViewModel - Allows data to survive configuration changes such as screen rotations.
*	LiveData - Lifecycle-aware data holder class to respects the lifecycle Fragments.
*	AndroidX - Complete project implemented using AndroidX libraries.
*	Room database for data persistence.

# Design
*	The application is based on MVVM design pattern.

# Further enhancements
*	Handle configuration changes
*	Api error handling
*	Progress bar to show loading while fetching the data from the API
*	Improving the data source fetch using Paging 
*	Furthermore cosmetics and refactoring is an endless thought.
*	Navigation component

# Snapshots

* With Internet
* ![image](https://user-images.githubusercontent.com/92021804/142850293-4b26c3ef-2f4d-4a04-be51-10c09ca4edb1.png)

* Without Internet and saved data in DB
* ![image](https://user-images.githubusercontent.com/92021804/142850457-bbb4bb80-4352-4915-becb-1214759d0bd3.png)





