# Art Finder

This application gets all it's data from Rijks museum API and displayed in a grid grouped by artist name. When user click on an art item, he can see details view with more infromation about the art piece.
https://data.rijksmuseum.nl/object-metadata/api/

Features:
1. Grouped grid of art pieces by artist name.
2. Paging of grid data.
3. Generic search for an art piece.

Different components/libraries used in the project
Clean Architecture This app is structured using single module clean architecture

Jetpack Compose For creating the screens

Compose navigation For navigation in the app

Paging 3 For achieving pagination

ViewModel The presentation layer uses the viewmodel pattern

Hilt For dependency injection

Coil For displaying images

Coroutines For handling async work

Retrofit For networking tasks

Mockk For mocking dependencies during testing
