package com.example.largefilecopy

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
 
import android.os.Environment
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
          result.success(copyDatabaseIfNeeded2(fileName))
        } else {
          result.success("Android could not extract flutter arguments in method: (copyLargeFile)")
        } 
      }
      "getPlatformVersion" -> result.success("Running on Android: ${android.os.Build.VERSION.RELEASE}")
      else -> result.success("Android calling method not recognized")
    }
  }

  private fun copyDatabaseIfNeeded(fileName: String): String {
    return fileName + "hello"
  }

  private fun copyDatabaseIfNeeded2(fileName: String): String {

    // val myText: String = application.assets.open(currencies).bufferedReader().use{ it.readText() }
    val myText: String = mRegistrar.context().assets.open("testy.txt").bufferedReader().use {
      it.readText()
    }
    return myText

    // AssetManager assets = getAssets()

    // val afile = assets.open( "truite.rive" )
    // val bfile = File(Environment.getExternalStorageDirectory().toString() + "/Rivescript/Bots/Bfile.rive")
    
    // var inStream: InputStream? = null
    // var outStream: OutputStream? = null

    // inStream = afile
    // outStream = FileOutputStream(bfile)

    // val buffer = ByteArray(1024)
    // var length = inStream.read(buffer)

    // while (length    > 0 )
    // {
    //     outStream.write(buffer, 0, length)
    //     length = inStream.read(buffer)
    // }
    // inStream.close()
    // outStream.close()      
    // return "hello3"
  }
}
