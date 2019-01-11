# large_file_copy

Due to the fact that Flutter does not allow large file copies yet (at least that is what seems to be the case by the end of 2018), we created this Plugin.

The Plugin copies a large File natively from the native-Bundle to your Application Documents Folder (and of course, only if the file does not exist already at the Application Documents Folder location [same name])

The return-value of the copyDB method delivers the fullNamePath of the copied (or already existing) large File inside the Application Documents Folder.

## Getting Started (native preparations)

The following preparation is needed as for the native worlds respectively.
(i.e. in both worlds, you need to provide the large-file inside the corresponding native Bundle...)

iOS:
- open your Flutter-project's /iOS/Runner.xcworkspace in Xcode
- inside Runner, create a new Group and call it for example "Bundle"
- inside Bundle (or whatever you called your Group), drag and drop your large File that you need to use inside Flutter
  (make sure to select "Copy items if needed")
(optional: to check if all is correct - go to your iOS/Runner/ Folder and see if the added large File is there...)

Android:

## How to use the large_file_copy Plugin inside your Flutter project

### 1. Inside pubspec.yaml file of your Flutter project, add the following dependency:

```yaml
dependencies:
  flutter:
    sdk: flutter
  large_file_copy:
    git:
      url: https://github.com/iKK001/large_file_copy.git
```

### 2. Import the package

```dart
import 'package:large_file_copy/large_file_copy.dart';
```

### 3. Inside one of your StatefulWidget's State, use the **large_file_copy** plugin

```dart
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String fullPathName;
    try {
      fullPathName = await LargeFileCopy("myLargeFileName.db").copyDB;
    } on PlatformException {
      fullPathName = 'Failed to copy the DB.';
    }
    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    print(fullPathName); // this is the location of your large-file now...
  }
```

### 4. you can now use your large File from the Application Documents folder 

The path to the file is given by the **fullPathName** return value of the native LargeFileCopy("myLargeFileName.db").copyDB method.


## Additional general Information for using Flutter Plugins

This project is a starting point for a Flutter
[plug-in package](https://flutter.io/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our 
[online documentation](https://flutter.io/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
