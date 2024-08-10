<p align="center">
    <img  src="https://github.com/shamim-emon/Notes/blob/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png">
</p>
<h1 align="center"><b>Notes</b></h1>  
<p align="center">    
 Notes demonstrates modern Android development with <b>Jetpack Compose</b>, <b>Hilt</b>,  <b>Coroutines</b>, <b>Jetpack Components (Room, ViewModel,LiveData)</b>, and Material Design based on MVVM clean architecture.  
</p>  
<p align="center">  
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>  
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a></p>  
<br/>  


|                         |                         |                         |
|:-----------------------:|:-----------------------:|:------------------------|
| ![](/previews/sc_1.jpg) | ![](/previews/sc_2.jpg) | ![](/previews/sc_3.jpg) |
|                         |                         |                         |

## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/)
- <b>Jetpack Compose</b>: Jetpack Compose is Android’s recommended modern toolkit for building native UI.
-  <b>[Coroutines](https://kotlinlang.org/docs/coroutines-guide.html)</b>: A _coroutine_ is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
- <b>ViewModel</b>: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
- <b>Room</b>: Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Hilt](https://dagger.dev/hilt/): for dependency injection.
- **Navigation Component** : The [Navigation component](https://developer.android.com/guide/navigation) provides support for Jetpack Compose applications.
- <b>Junit</b>(Unit Test) : A simple framework to write repeatable tests.
- <b>Mockito</b>(Unit Test) : A mocking framework that lets you write beautiful tests with a clean & simple API.

<br/>  

## Architecture
This app uses MVVM clean Architecture.

|                                 |
|:-------------------------------:|
| ![](/previews/architecture.png) |
|                                 | 

<br/>  

## Testing
Local Tests are done using `Junit` & `Mockito`.

<br/>  


## How To Contribute
At the moment this repo only have unit/local tests.Contribution in `androidTest`/ `UITest` is welcome.

<br/>  

## License
```xml  
Designed and developed by 2024 shamim-emon (Shamim Shahrier Emon)  
  
Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at  
  
 http://www.apache.org/licenses/LICENSE-2.0  
Unless required by applicable law or agreed to in writing, software  
distributed under the License is distributed on an "AS IS" BASIS,  
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions and  
limitations under the License.  
```