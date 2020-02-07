# tokTales Engine

> A neat game engine.

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Download](https://api.bintray.com/packages/tokelon/tokTales/tokTales/images/download.svg) ](https://bintray.com/tokelon/tokTales/tokTales/_latestVersion)
![tokTales-master CI](https://github.com/Tokelon/tokTales/workflows/tokTales-master%20CI/badge.svg)

A cross-platform game engine written in Java.

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

## Documentation

*[Coming Soon]*

## Licence

- **[MIT license](https://opensource.org/licenses/MIT)**
- Copyright 2020 © Elias Paralikes
