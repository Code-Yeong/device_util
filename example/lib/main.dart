import 'dart:async';

import 'package:device_util/device_util.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  String _versionName = 'Unknown';
  String _versionCode = 'Unknown';
  Map<String, String> _channelInfo = {
    "first_install_channel": "Unknown",
    "current_install_channel": "Unknown"
  };

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    String versionName;
    String versionCode;
    Map<String, String> channelInfo;
    try {
      platformVersion = await DeviceUtil.platformVersion;
      versionName = await DeviceUtil.versionName;
      versionCode = await DeviceUtil.versionCode;
      channelInfo = await DeviceUtil.getChannelInfo;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
      _versionName = versionName;
      _versionCode = versionCode;
      _channelInfo = channelInfo;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: ListView(
          children: [
            ListTile(
              title: Text('Running on:'),
              subtitle: Text('$_platformVersion'),
            ),
            ListTile(
              title: Text('Version name is:'),
              subtitle: Text('$_versionName'),
            ),
            ListTile(
              title: Text('Version code is:'),
              subtitle: Text('$_versionCode'),
            ),
            ListTile(
              title: Text('Channels:'),
              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text('1. Last channel was: ${_channelInfo.values.first}\n'),
                  Text('2. Current channel is: ${_channelInfo.values.last}\n'),
                ],
              ),
            ),
            ListTile(
              title: Text('Open application setting page'),
              trailing: Icon(Icons.chevron_right),
              onTap: () {
                DeviceUtil.openApplicationSettingPage();
              },
            ),
            ListTile(
              title: Text('Open network setting page'),
              trailing: Icon(Icons.chevron_right),
              onTap: () {
                DeviceUtil.openNetworkSettingPage();
              },
            ),
            ListTile(
              title: Text('Open app store comment page'),
              trailing: Icon(Icons.chevron_right),
              onTap: () {
                DeviceUtil.openAppStoreCommentPage('appId');
              },
            ),
            ListTile(
              title: Text('Minimized window(Windows only)'),
              trailing: Icon(Icons.chevron_right),
              onTap: () {
                DeviceUtil.minimizeWindow();
              },
            ),
            ListTile(
              title: Text('Kill app'),
              trailing: Icon(Icons.chevron_right),
              onTap: () {
                DeviceUtil.killApp();
              },
            ),
          ],
        ),
      ),
    );
  }
}
