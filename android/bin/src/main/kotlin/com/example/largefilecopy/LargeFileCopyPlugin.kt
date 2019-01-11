package com.example.largefilecopy

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class LargeFileCopyPlugin: MethodCallHandler {
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "ch.ideenkaffee.largefilecopy")
      channel.setMethodCallHandler(LargeFileCopyPlugin())
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {

    // flutter cmds dispatched on Android device :
    if (call.method == "copyLargeFile") {

      if call.arguments != nil {
        if let myArgs = args as? [String: Any],
           let fileName = myArgs["fileName"] as? String {
          result.success(copyDatabaseIfNeeded(fileName: fileName))
        }
      } else {
        result.success("Android could not extract flutter arguments in method: (copyLargeFile)")
      }
    } else if call.method == "getPlatformVersion" {
      result.success("Running on: Android " + "${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

    fun copyDatabaseIfNeeded(fileName: String): String {
        return "hello"
    }
}
