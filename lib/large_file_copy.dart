import 'dart:async';

import 'package:flutter/services.dart';

class LargeFileCopy {

  final String fileName;
  LargeFileCopy(this.fileName);

  static const MethodChannel _channel =
      const MethodChannel('ch.ideenkaffee.largefilecopy');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<String> get copyLargeFile async {		
    final String fullPathName = await _channel.invokeMethod('copyLargeFile',<String, dynamic> {		
        'fileName': this.fileName,		
      });		
    return fullPathName;		
  }
}
