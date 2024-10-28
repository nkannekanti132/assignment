# Fetch Rewards Coding Exercise

## Overview

This project is a native Android application built using Kotlin that fulfills the requirements of the Fetch Rewards coding exercise. The app retrieves and displays data from a provided API in an organized and user-friendly manner.

## Key Features

- **Data Retrieval**: Fetches data from [https://fetch-hiring.s3.amazonaws.com/hiring.json](https://fetch-hiring.s3.amazonaws.com/hiring.json).
- **Grouping and Sorting**: Displays items grouped by `listId`, sorted first by `listId` and then by `name`.
- **Filtering**: Excludes items with a blank or null `name`.
- **Offline Availability**: Uses Room for local database caching to ensure data is available offline.
- **Search and Filter**: Additional features for searching and filtering items based on `listId`. Long-pressing an item displays detailed information about that item.

## Architecture

- **MVVM & Clean Architecture**: The application utilizes the Model-View-ViewModel (MVVM) and clean architecture for clear separation of concerns.
- **Hilt for Dependency Injection**: Implements Hilt to manage dependencies efficiently, enhancing code maintainability.
- **Jetpack Compose**: The user interface is built using Jetpack Compose for a declarative UI design.


## Demo
https://github.com/user-attachments/assets/1965365d-bf1c-4bf2-a973-f01fe1f2dc3d



