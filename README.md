# large_file_copy

Due to the fact that Flutter does not allow large file copies yet (at least that is what seems to be the case by January of 2019), I created this Plugin.

The following code is normally used to copy a small file from the assets folder to the App Documents folder where it can be used further...

```dart
Directory directory = await getApplicationDocumentsDirectory();
var dbPath = join(directory.path, "app.db");
if (FileSystemEntity.typeSync(dbPath) == FileSystemEntityType.notFound) {
  ByteData data = await rootBundle.load("assets/my_db_file.db");
  List<int> bytes = data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes);
  await File(dbPath).writeAsBytes(bytes);
}
```
However, I found that the above code does not work for large files !
Therefore the motivation for this plugin (i.e. to make the file-copy natively for each platform).

The Plugin copies a large File natively ...
- from the native-Bundle (iOS)...
- from the assets-folder (Android)...

--> ...and places it to your Application Documents Folder

(of course, it does the copying only if the file does not already exist at the Application Documents Folder location [same name]).

The plugin method created is called **copyLargeFile**

The return-value of the copyLargeFile method delivers the fullNamePath of the copied (or already existing) large File inside the Application Documents Folder.

## Getting Started (native preparations)

The following preparation is needed as for the native worlds respectively.
(i.e. in both worlds, you need to provide the large-file inside the corresponding native Bundle or Assets-folder...)

iOS:
- open your Flutter-project's /ios/Runner.xcworkspace in Xcode
- inside Runner, create a new Group and call it for example "Bundle"
- inside Bundle (or whatever you called your Group), drag and drop your large File (make sure to select "Copy items if needed")
- (optional): to check if all is correct for iOS - go to your Flutter project's ios/Runner/ Folder and see if the added large File is there...

Android:

- open your Flutter-project's /android/my_flutter_project_name_android.iml in Android Studio
- inside app/app/src/main/assets/ folder drag and drop your large file (create assets folder if it is not already there)
- (optional): to check if all is correct for Android - go to your Flutter project's android/src/main/assets/ Folder and see if the added large File is there...

## How to use the large_file_copy Plugin inside your Flutter project

### 1. Inside pubspec.yaml file of your Flutter project, add the following dependency:

```yaml
dependencies:
  flutter:
    sdk: flutter
  large_file_copy: ^0.0.1
```
(hint: the version might be different in the meantime. Make sure to include the newest version of the package plugin).

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
      platformVersion = await LargeFileCopy.platformVersion;
      print(platformVersion);
      fullPathName = await LargeFileCopy("testy.txt").copyLargeFile;
      print(fullPathName);
    } on PlatformException {
      print('Failed to copy the Large File.');
      return;
    }

    if (!mounted) return;

    print(fullPathName); // this is the location of your large-file now. Use it for anything in your Flutter app.
  }
```

### 4. you can now use your large File from the Application Documents folder 

The path to the file is given by the **fullPathName** return value of the native LargeFileCopy("myLargeFileName.db").copyDB method.
