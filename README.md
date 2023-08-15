# Art Finder

Simple app showing art pieces from Rijks museum. UI is made by Jetpack Compose and Clean Architecture.

It gets its data from [Rijks museum API](https://data.rijksmuseum.nl/object-metadata/api/) and displays it in a grid grouped by artist name. When clicking on an art item, details view is displayed with more information about that art piece.

# Features:

1. Grouped grid of art pieces by artist name.
2. Paging of grid data.
3. Generic search for an art piece.
4. Details of art piece.

# Different components/libraries used in the project

* **Clean Architecture**: this app is structured using clean architecture

* **Jetpack Compose**: used for creating the screens' UI

* **Compose navigation**: used for navigation in the app

* **Paging 3**: used for achieving pagination

* **ViewModel**: The presentation layer uses the viewmodel pattern

* **Hilt**: used for dependency injection

* **Coil**: used for displaying images

* **Coroutines**: used for handling async work

* **Retrofit**: used for networking tasks

* **Mockk**: used for mocking dependencies during testing
