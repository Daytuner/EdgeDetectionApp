# 🎨 Real-Time Edge Detection Android App

A high-performance Android application that performs real-time Canny edge detection on camera feed using OpenCV and JNI/NDK.

![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Kotlin%20%2B%20C%2B%2B-blue.svg)
![OpenCV](https://img.shields.io/badge/OpenCV-4.10.0-red.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

## 📱 Features

- **Real-Time Processing**: 20-30 FPS edge detection on device
- **Native Performance**: OpenCV C++ implementation via JNI
- **Camera2 API**: Modern Android camera integration
- **Save Frames**: Export processed images to device storage
- **Live Preview**: Immediate visual feedback with FPS counter
- **Optimized Resolution**: 640x480 for balanced quality and performance

## 🏗️ Architecture
```
┌─────────────────────────┐
│   Android UI Layer      │
│   (Kotlin/ImageView)    │
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│   Camera2 API Layer     │
│   (YUV_420_888 frames)  │
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│   JNI Bridge Layer      │
│   (Kotlin ↔ C++)        │
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│   OpenCV Processing     │
│   (C++ Native Code)     │
│   • Gaussian Blur       │
│   • Canny Edge (50,150) │
└─────────────────────────┘
```

## 🛠️ Technologies Used

### Android Layer
- **Language**: Kotlin
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36
- **Camera**: Camera2 API
- **Build System**: Gradle 8.7 + CMake 3.22.1

### Native Layer
- **Language**: C++17
- **Computer Vision**: OpenCV 4.10.0
- **NDK**: Android NDK r26
- **JNI**: Java Native Interface for Kotlin-C++ communication

### Web Viewer
- **Language**: TypeScript
- **Frontend**: HTML5 + CSS3
- **Purpose**: Documentation and statistics display

## 📋 Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17
- **Android SDK**: API 24-36
- **NDK**: r26 or later
- **CMake**: 3.22.1+
- **OpenCV Android SDK**: 4.10.0
- **Node.js**: 16+ (for web viewer)

## 🚀 Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/EdgeDetectionApp.git
cd EdgeDetectionApp
```

### 2. Download OpenCV Android SDK

1. Download OpenCV 4.10.0 for Android from [opencv.org](https://opencv.org/releases/)
2. Extract to: `C:\OpenCV-android-sdk\` (Windows) or adjust path in setup

### 3. Copy OpenCV Native Libraries

Copy `.so` files from OpenCV SDK to project:
```bash
# From: C:\OpenCV-android-sdk\sdk\native\libs\
# To: EdgeDetectionApp\app\src\main\jniLibs\

# Structure should be:
jniLibs/
  ├── arm64-v8a/
  │   ├── libopencv_java4.so
  │   └── libc++_shared.so
  └── armeabi-v7a/
      ├── libopencv_java4.so
      └── libc++_shared.so
```

### 4. Update CMakeLists.txt (if needed)

Ensure OpenCV path in `app/src/main/cpp/CMakeLists.txt` matches your installation:
```cmake
set(OpenCV_DIR "C:/OpenCV-android-sdk/sdk/native/jni")
```

### 5. Open in Android Studio

1. Open Android Studio
2. File → Open → Select `EdgeDetectionApp` folder
3. Wait for Gradle sync to complete
4. Build → Clean Project
5. Build → Rebuild Project

## 📱 Running the App

### On Physical Device (Recommended)

1. **Enable Developer Options** on your Android device:
    - Settings → About Phone → Tap "Build Number" 7 times

2. **Enable USB Debugging**:
    - Settings → Developer Options → USB Debugging (ON)

3. **Connect device** via USB and allow debugging

4. **Run** in Android Studio:
    - Select your device from dropdown
    - Click green play button ▶️

### On Emulator

1. **Create AVD** with:
    - API Level 36
    - x86_64 architecture
    - Google APIs

2. **Run** app on emulator

**Note**: Real device recommended for better camera performance.

## 📂 Project Structure
```
EdgeDetectionApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── cpp/                    # Native C++ code
│   │   │   │   ├── native-lib.cpp      # JNI implementation
│   │   │   │   └── CMakeLists.txt      # CMake build config
│   │   │   ├── java/com/example/edgedetectionapp/
│   │   │   │   ├── MainActivity.kt     # Main activity
│   │   │   │   ├── CameraManager.kt    # Camera handling
│   │   │   │   └── NativeProcessor.kt  # JNI bridge
│   │   │   ├── jniLibs/                # OpenCV .so files
│   │   │   │   ├── arm64-v8a/
│   │   │   │   └── armeabi-v7a/
│   │   │   ├── res/                    # Android resources
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle.kts
│   └── build.gradle.kts
├── web/                                # TypeScript web viewer
│   ├── index.html
│   ├── style.css
│   ├── app.ts
│   └── package.json
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── .gitignore
└── README.md
```

## 🎯 Key Components

### MainActivity.kt
- Manages camera permissions
- Handles frame processing callbacks
- Updates UI with FPS metrics
- Saves processed frames to storage

### CameraManager.kt
- Initializes Camera2 API
- Configures YUV_420_888 capture
- Provides frame data to processing pipeline

### NativeProcessor.kt
- JNI bridge between Kotlin and C++
- Loads native libraries
- Exposes native methods to Kotlin

### native-lib.cpp
- OpenCV integration
- Implements Canny edge detection
- YUV to RGB conversion
- Gaussian blur preprocessing

## ⚡ Performance Metrics

| Metric | Value |
|--------|-------|
| **FPS** | 20-30 (on real device) |
| **Processing Time** | 3-5ms per frame |
| **Resolution** | 640x480 |
| **Algorithm** | Canny Edge Detection |
| **Thresholds** | Low: 50, High: 150 |
| **Memory Usage** | ~50MB |

## 🔧 Algorithm Details

### Canny Edge Detection Pipeline

1. **Color Conversion**: YUV_420_888 → RGB → Grayscale
2. **Noise Reduction**: Gaussian Blur (5x5 kernel, σ=1.5)
3. **Edge Detection**: Canny algorithm (threshold: 50-150)
4. **Output**: White edges on black background
5. **Display**: RGB conversion for rendering

### Processing Code
```cpp
// Convert YUV to RGB
cv::Mat yuvMat(height + height/2, width, CV_8UC1, inputBytes);
cv::Mat rgbMat;
cv::cvtColor(yuvMat, rgbMat, cv::COLOR_YUV2RGB_NV21);

// Convert to grayscale
cv::Mat grayMat;
cv::cvtColor(rgbMat, grayMat, cv::COLOR_RGB2GRAY);

// Apply Gaussian blur
cv::Mat blurredMat;
cv::GaussianBlur(grayMat, blurredMat, cv::Size(5, 5), 1.5);

// Apply Canny edge detection
cv::Mat edgesMat;
cv::Canny(blurredMat, edgesMat, 50, 150);

// Convert back to RGB for display
cv::Mat resultMat;
cv::cvtColor(edgesMat, resultMat, cv::COLOR_GRAY2RGB);
```

## 📸 Usage

1. **Launch App**: Open EdgeDetectionApp on your device
2. **Grant Permissions**: Allow camera access when prompted
3. **View Edges**: Real-time edge detection starts automatically
4. **Monitor Performance**: Check FPS counter at bottom of screen
5. **Save Frame**: Tap "Save Frame" button to export current image
6. **Access Saved Images**: Gallery → Pictures → EdgeDetection folder

## 🌐 Web Viewer

The project includes a TypeScript-based web documentation viewer.

### Setup Web Viewer
```bash
cd web
npm install
npx tsc app.ts
```

### Run Web Viewer
```bash
# Option 1: Direct open
# Double-click index.html in file explorer

# Option 2: HTTP server
npx http-server
# Open: http://localhost:8080
```

The web viewer displays:
- Application architecture
- Technology stack
- Performance metrics
- Algorithm details

## 🐛 Troubleshooting

### Build Errors

**Problem**: `libopencv_java4.so not found`

**Solution**:
```bash
# Ensure .so files exist:
# app/src/main/jniLibs/arm64-v8a/libopencv_java4.so
# app/src/main/jniLibs/arm64-v8a/libc++_shared.so
# Copy from OpenCV SDK native/libs/ folder
```

**Problem**: CMake configuration failed

**Solution**:
```bash
# Update OpenCV path in CMakeLists.txt
# Verify OpenCV SDK extracted correctly
# File → Invalidate Caches → Invalidate and Restart
```

**Problem**: JVM target compatibility error

**Solution**:
```kotlin
// In app/build.gradle.kts
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlinOptions {
    jvmTarget = "17"
}
```

### Runtime Errors

**Problem**: Camera permission denied

**Solution**:
```bash
# Grant camera permission manually:
# Settings → Apps → EdgeDetectionApp → Permissions → Camera → Allow
```

**Problem**: Black screen / no edge detection

**Solution**:
1. Check Logcat for errors (package:mine filter)
2. Verify OpenCV libraries loaded successfully
3. Restart device
4. Uninstall and reinstall app

**Problem**: App crashes on launch

**Solution**:
```bash
# Check Logcat for UnsatisfiedLinkError
# Ensure all .so files present in jniLibs
# Clean and rebuild project
```

## 🔮 Future Enhancements

- [ ] Toggle between raw camera and edge detection views
- [ ] Adjustable Canny threshold sliders (UI controls)
- [ ] Multiple edge detection algorithms (Sobel, Laplacian, Prewitt)
- [ ] OpenGL ES rendering for improved performance
- [ ] Real-time FPS graph display
- [ ] Video recording of processed output
- [ ] Multiple color schemes for edge visualization (green, blue, rainbow)
- [ ] Face detection integration
- [ ] Object tracking capabilities
- [ ] Batch processing of images from gallery
- [ ] Cloud sync for processed images

## 📝 Development Notes

### Modifying Native Code

1. Edit `app/src/main/cpp/native-lib.cpp`
2. Clean and rebuild project
3. Native library automatically bundled in APK

### Adding New Processing Algorithms

**Step 1**: Add C++ function in `native-lib.cpp`:
```cpp
extern "C" JNIEXPORT void JNICALL
Java_com_example_edgedetectionapp_NativeProcessor_sobelEdgeDetection(
    JNIEnv* env, jobject, jbyteArray input, 
    jint width, jint height, jbyteArray output) {
    // Implementation
}
```

**Step 2**: Declare in `NativeProcessor.kt`:
```kotlin
external fun sobelEdgeDetection(
    input: ByteArray, 
    width: Int, 
    height: Int, 
    output: ByteArray
)
```

**Step 3**: Call from Kotlin code

### Performance Optimization Tips

- Reduce resolution for higher FPS
- Adjust Canny thresholds based on lighting
- Use smaller Gaussian blur kernel for speed
- Consider GPU acceleration via OpenGL ES
- Profile with Android Profiler

## 📊 Testing

### Unit Tests
```bash
./gradlew test
```

### Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Performance Testing
- Monitor FPS counter during use
- Check memory usage in Android Profiler
- Test on different devices and Android versions

## 🏆 Achievements

✅ Real-time edge detection at 20-30 FPS  
✅ Native OpenCV integration via JNI  
✅ Camera2 API implementation  
✅ Cross-platform compatibility (arm64-v8a, armeabi-v7a)  
✅ Save processed frames functionality  
✅ TypeScript web documentation  
✅ Comprehensive error handling

## 📄 License

This project is licensed under the MIT License.
```
MIT License

Copyright (c) 2025 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👨‍💻 Author

**Your Name**
- Email: your.email@example.com
- GitHub: [@yourusername](https://github.com/Daytuner)
- LinkedIn: [Your LinkedIn](https://www.linkedin.com/in/ayush-raj-panda-37152b1b3/)

## 🙏 Acknowledgments

- [OpenCV](https://opencv.org/) - Open Source Computer Vision Library
- [Android Developers](https://developer.android.com/) - Camera2 API Documentation
- [JetBrains](https://www.jetbrains.com/) - Kotlin Programming Language
- Stack Overflow Community - Troubleshooting assistance

## 📚 References

- [OpenCV Android SDK Documentation](https://docs.opencv.org/4.x/d5/df8/tutorial_dev_with_OCV_on_Android.html)
- [Android Camera2 API Guide](https://developer.android.com/training/camera2)
- [JNI Programming Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/jni/)
- [Canny Edge Detection Algorithm](https://en.wikipedia.org/wiki/Canny_edge_detector)

## 📧 Support

For questions, issues, or feature requests:
- Open an issue on GitHub
- Email: your.email@example.com
- Discussions: [GitHub Discussions](https://github.com/Daytuner/EdgeDetectionApp/discussions)

## 🔄 Version History

- **v1.0.0** (2025-10-31)
    - Initial release
    - Real-time Canny edge detection
    - Camera2 API integration
    - Save frame functionality
    - TypeScript web viewer

---

**⭐ If you find this project useful, please give it a star on GitHub!**

**Built with ❤️ using Android, OpenCV, Kotlin, and C++**