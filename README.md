# Rentomatic

Rentomatic is an Android application designed to help users manage rental properties. This project is built using Android Studio and Gradle.

## Table of Contents

- [Description](#description)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
  - [Firebase Configuration](#firebase-configuration)
  - [ProGuard Configuration](#proguard-configuration)
- [Dependencies](#dependencies)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Description

Rentomatic is an application that allows landlords to list their rental properties and tenants to find and rent properties.

## Getting Started

### Prerequisites

- Android Studio
- Java Development Kit (JDK) 8 or higher
- Android SDK

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/rentomatic.git
    cd rentomatic
    ```

2. Open the project in Android Studio.

3. Configure the local properties:
    - Open [local.properties](http://_vscodecontentref_/0) and set the `sdk.dir` to the location of your Android SDK.

## Usage

To run the project, use Android Studio to build and deploy the app to an emulator or a connected device.


## Configuration

### Firebase Configuration

The project integrates with Firebase for analytics and authentication. Configuration settings can be found in [build.gradle](http://_vscodecontentref_/1) and [appInsightsSettings.xml](http://_vscodecontentref_/2).

### ProGuard Configuration

ProGuard rules are defined in [proguard-rules.pro](http://_vscodecontentref_/3).

## Dependencies

Key dependencies used in the project include:

- AndroidX libraries
- Firebase libraries
- Glide for image loading

Dependencies are managed in [build.gradle](http://_vscodecontentref_/4).

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [Android Developers](https://developer.android.com/)
- [Firebase](https://firebase.google.com/)
- [Glide](https://github.com/bumptech/glide)
