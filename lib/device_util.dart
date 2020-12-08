import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class DeviceUtil {
  static const MethodChannel _channel = const MethodChannel('device_util');

  /// The version name of the platform
  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// The version name of this application
  static Future<String> get versionName async {
    final String version = await _channel.invokeMethod('getVersionName');
    return version;
  }

  /// The version code name of this application
  static Future<String> get versionCode async {
    final String version = await _channel.invokeMethod('getVersionCode');
    return version;
  }

  /// Open the phone's network settings page
  static Future<Null> openNetworkSettingPage() async {
    await _channel.invokeMethod('launchNoNetwork');
  }

  /// Open the settings page of the currently running application
  static Future<Null> openApplicationSettingPage() async {
    await _channel.invokeMethod('systemSettingPage');
  }

  /// Get channel information of the current application
  ///
  /// The length of the array is 2, the first value represents the historical version number,
  /// and the second value represents the current version number
  ///
  /// On Android, athe channel configuration files are in the directories
  /// "app/src/main/assets/channel.ini" and "app/src/main/assets/default_channel.ini",
  /// and you can see these two files in the "example/android/app/src/main/assets" directory
  static Future<Map<String, String>> get getChannelInfo async {
    final Map<String, String> channelInfo = Map<String, String>.from(await _channel.invokeMethod('getChannelInfo'));
    return channelInfo;
  }

  // /// Open the scoring page of the current application in the app store
  // static void openAppStoreCommentPage(String appID) async {
  //   await _channel.invokeMethod('openAppStoreComment', {'appId', appID});
  // }

  /// Kill the current application process and exit (Only supports Android)
  static Future<Null> killApp() async {
    if (Platform.isAndroid) {
      await _channel.invokeMethod('killApp');
    }
  }
}
