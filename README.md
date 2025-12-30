# LabelLens 📷🔍

LabelLens is an Android app powered by Google ML Kit that performs text recognition (OCR) on posters & images and Image labeling for animals and real-world objects. It saves scan history and auto-fills structured information such as product name, price, store, and other details.

## ✨ Features

### 🧠 SmartFill (OCR)
- Scan posters, flyers, or documents
- Detect text using **ML Kit Text Recognition**
- Automatically extract and autofill:
  - Title / Name
  - Date
  - Location or Description
- Three input options:
  - Pick image from gallery
  - Take a photo
  - Live camera text scanning (CameraX)

### 🏷️ Image Labeling
- Detect objects in images (animals, food, objects, etc.)
- Uses **ML Kit Image Labeling**
- Works with camera or gallery images

### 🕘 Scan History
- Automatically saves scan results
- View previously scanned text and labels
- Useful for tracking meetups, posters, or references

## 🛠 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **CameraX**
- **Google ML Kit**
  - Text Recognition (OCR)
  - Image Labeling
- **AndroidX**
- **Material 3**
