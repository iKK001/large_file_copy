import Flutter
import UIKit

public class SwiftLargeFileCopyPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "ch.ideenkaffee.largefilecopy", binaryMessenger: registrar.messenger())
    let instance = SwiftLargeFileCopyPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {

    // flutter cmds dispatched on iOS device :
    if call.method == "copyLargeFile" {

      guard let args = call.arguments else {
        return
      }

      if let myArgs = args as? [String: Any],
         let fileName = myArgs["fileName"] as? String {
          result(copyDatabaseIfNeeded(fileName: fileName))
      } else {
        result("iOS could not extract flutter arguments in method: (copyLargeFile)")
      } 

    } else if call.method == "getPlatformVersion" {
      result("Running on iOS: " + UIDevice.current.systemVersion)
    } else {
      result("iOS calling method not recognized")
    }
  }
}

// Copy file from bundle to documents folder and return its destination path
// (only copy if not existing already with same name)
private func copyDatabaseIfNeeded(fileName: String) -> String {
    
    let fileManager = FileManager.default
    let documentsUrl = fileManager.urls(for: .documentDirectory,
                                        in: .userDomainMask)
    guard documentsUrl.count != 0 else {
        return "Could not find documents URL"
    }

    let finalDatabaseURL = documentsUrl.first!.appendingPathComponent(fileName)
    
    if !( (try? finalDatabaseURL.checkResourceIsReachable()) ?? false) {
        let documentsURL = Bundle.main.resourceURL?.appendingPathComponent(fileName)
        do {
            try fileManager.copyItem(atPath: (documentsURL?.path)!, toPath: finalDatabaseURL.path)
            return "\(finalDatabaseURL.path)"
        } catch let error as NSError {
            return "Couldn't copy file to final location! Error:\(error.description)"
        }
    } else {
        return "\(finalDatabaseURL.path)"
    }
}
