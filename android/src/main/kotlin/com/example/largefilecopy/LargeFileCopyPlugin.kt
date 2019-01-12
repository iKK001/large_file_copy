package com.example.largefilecopy

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
 
import java.io.File
import java.io.InputStream
import io.flutter.util.PathUtils

class LargeFileCopyPlugin: MethodCallHandler {

  private final val mRegistrar: Registrar

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "ch.ideenkaffee.largefilecopy")
      channel.setMethodCallHandler(LargeFileCopyPlugin(registrar))
    }
  }

  constructor(registrar : Registrar) {
    this.mRegistrar = registrar
  }

  override fun onMethodCall(call: MethodCall, result: Result) {

    // flutter cmds dispatched on Android device :
    when (call.method) {
      "copyLargeFile" -> {
        val args = call.arguments as Map<String, Any>
        val fileName = args["fileName"] as String?
        if (fileName != null) {
          result.success(copyDatabaseIfNeeded(fileName))
        } else {
          result.success("Android could not extract flutter arguments in method: (copyLargeFile)")
        } 
      }
      "getPlatformVersion" -> result.success("Running on Android: ${android.os.Build.VERSION.RELEASE}")
      else -> result.success("Android calling method not recognized")
    }
  }

  private fun copyDatabaseIfNeeded(fileName: String): String {

    val assetStream: InputStream = mRegistrar.context().assets.open(fileName)
    val appliationDocumentsFolderPath: String = PathUtils.getDataDirectory(mRegistrar.context())
    val outputFilePath: String = appliationDocumentsFolderPath + "/" + fileName

    if (!File(outputFilePath).exists()) {
      File(outputFilePath).copyInputStreamToFile(assetStream)   
    }
    return outputFilePath
  }

  private fun File.copyInputStreamToFile(inputStream: InputStream) {
    inputStream.use { input ->
        this.outputStream().use { fileOut ->
            input.copyTo(fileOut)
        }
    }
  }
}
