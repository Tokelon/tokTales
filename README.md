# tokTales Engine

> A neat game engine.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Download](https://api.bintray.com/packages/tokelon/tokTales/tokTales/images/download.svg) ](https://bintray.com/tokelon/tokTales/tokTales/_latestVersion)
![tokTales-master CI](https://github.com/Tokelon/tokTales/workflows/tokTales-master%20CI/badge.svg)

A cross-platform game engine written in Java.

## Contents

- [About](#about)
- [Disclaimer](#disclaimer)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Building](#building)
- [Documentation](#documentation)
- [Technologies](#technologies)
- [License](#license)

## About

The **tokTales Engine** is a personal project of mine that I've been working on in my free time for the past couple of years.  
It's goal is to provide a framework for creating games and other graphical applications.

## Disclaimer

Feel free to check out the samples and start working with the code, but keep in mind the following.

> **This project is currently in alpha.**  
It will contain bugs and all APIs are subject to change.

> **It is not feature complete.**  
The core structure has been implemented but a lot of functionality is still missing.

> **It is untested.**  
None of it has been tested in the wild.

> **The documentation needs additional work.**  
Not everything has Javadoc attached and the Wiki and Readme need to be expanded.

## Getting Started

- **[Demos](https://github.com/Tokelon/tokTales-demos)**
- **[Template Projects](https://github.com/Tokelon/tokTales-templates)**

## Installation

Add the package repository to your root project **build.gradle**.

```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```

Add the respective dependency to your subproject **build.gradle**.

### Core

```gradle
dependencies {
    implementation 'com.tokelon.toktales:tokTales-core-library:0.1.0'
}
```

### Desktop

```gradle
dependencies {
    implementation 'com.tokelon.toktales:tokTales-desktop-library:0.1.0'
}
```

### Android

```gradle
dependencies {
    implementation 'com.tokelon.toktales:tokTales-android-library:0.1.0'
}
```

### Modules

The engine is divided into modules: **Core**, **Desktop**, **Android**, etc.  
Each module contains one or more libraries: `core-library` and `core-test` in Core, `desktop-library` and `desktop-test` in Desktop, and so on.

The modules are built as a hierarchical structure in which platform libraries have dependencies on Core libraries, but never one platform on another. Meaning that, for example, you can use the Desktop libraries without ever getting involved with Android. But you can also develop for both and put the shared code into a core library.

There are also mixed modules like **Tools**, that contain libraries for more than one platform.

## Building

The build system used is **[Gradle](https://gradle.org/)**.

### Prerequisites

- An internet connection for downloading Gradle and project dependencies
- **[Java Development Kit](https://jdk.java.net/)** version 8 or higher for running Gradle
- **[Android SDK](https://developer.android.com/)** when building Android projects

### Building a project

To build the master project that includes all other projects, run the following in a shell (Windows).

    git clone https://github.com/Tokelon/tokTales.git
    cd tokTales-master
    .\gradlew build

### Project structure

Each of the [engine modules](#modules) is configured as a Gradle root project and contains one or more subprojects (libraries).  
In addition there is the master project which contains all other projects and is used for developing and publishing, as well as other tooling purposes.

## Documentation

*[Coming Soon]*

## Technologies

Below listed are some of the technologies used in this project.

- **[Java](https://www.java.com/)** - The programming language
- **[Gradle](https://gradle.org/)** - The build system
- **[LWJGL](https://www.lwjgl.org/)** (Desktop) - Bindings to native libraries
- **[Android SDK](https://developer.android.com/studio)** (Android) - Android API and toolchain
- **[OpenGL](https://www.opengl.org/)** - The default graphics API
- **[JOML](https://github.com/JOML-CI/JOML)** - OpenGL math
- **[Guice](https://github.com/google/guice)** - Dependency injection
- **[SLF4J](http://www.slf4j.org/)** - Logging
- **[Tiled Map Editor](https://www.mapeditor.org/)** (Optional) - Level design

## License

- **[MIT License](https://opensource.org/licenses/MIT)**
- Copyright 2020 © Elias Paralikes
