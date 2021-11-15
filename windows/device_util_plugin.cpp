#include "include/device_util/device_util_plugin.h"

// This must be included before many other Windows headers.
#include <windows.h>

// For getPlatformVersion; remove unless needed for your plugin implementation.
#include <VersionHelpers.h>

#include <flutter/method_channel.h>
#include <flutter/plugin_registrar_windows.h>
#include <flutter/standard_method_codec.h>

#include <map>
#include <memory>
#include <sstream>

namespace {

class DeviceUtilPlugin : public flutter::Plugin {
 public:
  static void RegisterWithRegistrar(flutter::PluginRegistrarWindows *registrar);

  DeviceUtilPlugin();

  virtual ~DeviceUtilPlugin();

 private:
  // Called when a method is called on this plugin's channel from Dart.
  void HandleMethodCall(
      const flutter::MethodCall<flutter::EncodableValue> &method_call,
      std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result);
};

// static
void DeviceUtilPlugin::RegisterWithRegistrar(
    flutter::PluginRegistrarWindows *registrar) {
  auto channel =
      std::make_unique<flutter::MethodChannel<flutter::EncodableValue>>(
          registrar->messenger(), "device_util",
          &flutter::StandardMethodCodec::GetInstance());

  auto plugin = std::make_unique<DeviceUtilPlugin>();

  channel->SetMethodCallHandler(
      [plugin_pointer = plugin.get()](const auto &call, auto result) {
        plugin_pointer->HandleMethodCall(call, std::move(result));
      });

  registrar->AddPlugin(std::move(plugin));
}

DeviceUtilPlugin::DeviceUtilPlugin() {}

DeviceUtilPlugin::~DeviceUtilPlugin() {}

void DeviceUtilPlugin::HandleMethodCall(
    const flutter::MethodCall<flutter::EncodableValue> &method_call,
    std::unique_ptr<flutter::MethodResult<flutter::EncodableValue>> result) {
  if (method_call.method_name().compare("getPlatformVersion") == 0) {
    std::ostringstream version_stream;
    version_stream << "Windows ";
    if (IsWindows10OrGreater()) {
      version_stream << "10+";
    } else if (IsWindows8OrGreater()) {
      version_stream << "8";
    } else if (IsWindows7OrGreater()) {
      version_stream << "7";
    }
    result->Success(flutter::EncodableValue(version_stream.str()));
  } else if (method_call.method_name().compare("getVersionName") == 0) {
    auto version_name = "windows unknown";
    result->Success(flutter::EncodableValue(version_name));
  } else if (method_call.method_name().compare("getVersionCode") == 0) {
    auto version_code = "windows unknown";
    result->Success(flutter::EncodableValue(version_code));
  } else if (method_call.method_name().compare("getChannelInfo") == 0) {
    auto channel_info_map = flutter::EncodableMap{};
    channel_info_map[flutter::EncodableValue("first_install_channel")] = flutter::EncodableValue("Windows");
    channel_info_map[flutter::EncodableValue("current_install_channel")] = flutter::EncodableValue("Windows");
    result->Success(flutter::EncodableValue(channel_info_map));
  } else if (method_call.method_name().compare("openAppStoreComment") == 0) {
    result->Success();
  } else if (method_call.method_name().compare("systemSettingPage") == 0) {
    result->Success();
  } else if (method_call.method_name().compare("killApp") == 0) {
    result->Success();
  }else if (method_call.method_name().compare("launchNoNetwork") == 0) {
    result->Success();
  } else {
    result->NotImplemented();
  }
}

}  // namespace

void DeviceUtilPluginRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar) {
  DeviceUtilPlugin::RegisterWithRegistrar(
      flutter::PluginRegistrarManager::GetInstance()
          ->GetRegistrar<flutter::PluginRegistrarWindows>(registrar));
}
