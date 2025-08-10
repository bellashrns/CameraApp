# CameraApp

**CameraApp** is a modern Android application written in **Kotlin** that demonstrates how to use **CameraX** to capture photos, store them in a local Room database, scan barcodes with **ML Kit**, and generate QR codes.  
It follows a **clean architecture** with clearly separated data, domain, and UI layers and leverages libraries such as Hilt, ZXing, Lottie, and Timber.

## Features

- **Camera Preview & Capture** – Capture high‑quality images with CameraX and store them locally.
- **Barcode Scanning** – Detect and read barcodes in real‑time using ML Kit.
- **QR Code Generation** – Generate QR codes from text input using ZXing.
- **Gallery View** – Browse captured images stored in the Room database.
- **Tabbed Navigation** – Switch between Gallery and QR Code Generator screens via a ViewPager.
- **Animations** – Lottie animations enhance the user experience.
- **Dependency Injection** – Uses Hilt for dependency management.

## Tech Stack

- **Language**: Kotlin
- **Camera API**: CameraX
- **Barcode Scanning**: Google ML Kit
- **QR Code Generation**: ZXing
- **Database**: Room
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **UI**: ViewPager2, Lottie Animations
- **Logging**: Timber
- **Coroutines & Flow** for async tasks

## Project Structure

```
app/
 ├── data/       # Room DB, DAO, repository implementations
 ├── domain/     # Entities, repository interfaces, use cases
 ├── ui/         # Activities, Fragments, Adapters, ViewModels
 ├── di/         # Hilt modules
 └── utils/      # Utility classes & extensions
```

## Getting Started

### Prerequisites
- Android Studio Iguana or newer
- JDK 17+
- Device or emulator with camera capability

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/bellashrns/CameraApp.git
   ```
2. Open in Android Studio.
3. Sync the project with Gradle.

### Run the App
- Build and run on a device or emulator with a camera.

### Permissions
The app requires the following permissions in `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
