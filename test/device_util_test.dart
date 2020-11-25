import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:device_util/device_util.dart';

void main() {
  const MethodChannel channel = MethodChannel('device_util');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await DeviceUtil.platformVersion, '42');
  });
}
