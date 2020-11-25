
import 'dart:async';

import 'package:flutter/services.dart';

class DeviceUtil {
  static const MethodChannel _channel =
      const MethodChannel('device_util');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
