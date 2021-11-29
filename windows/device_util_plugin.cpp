#include "include/device_util/device_util_plugin.h"

// This must be included before many other Windows headers.
#include <windows.h>
#include <winuser.h>

// For getPlatformVersion; remove unless needed for your plugin implementation.
#include <VersionHelpers.h>
#include <atlstr.h>
#pragma comment(lib,"version")

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

  // flutter::PluginRegistrarWindows * register_ = nullptr;
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
    wchar_t szAppFullPath[_MAX_PATH] = {0};
    GetModuleFileName(NULL, szAppFullPath, MAX_PATH);
    DWORD dwLen = GetFileVersionInfoSize(szAppFullPath, NULL);
    char *pszAppVersion = new char[dwLen + 1];
    std::string versionName = "1.0.0";
    if(pszAppVersion){
        memset(pszAppVersion, 0, sizeof(char)*(dwLen + 1));
        GetFileVersionInfo(szAppFullPath, NULL, dwLen, pszAppVersion);
        CString strVersion;
        UINT nLen(0);
        VS_FIXEDFILEINFO *pFileInfo(NULL);
        VerQueryValue(pszAppVersion, TEXT("\\"), (LPVOID*)&pFileInfo, &nLen);
        if(pFileInfo || true){
            strVersion.Format(TEXT("%d.%d.%d"),
                HIWORD(pFileInfo->dwFileVersionMS),
                LOWORD(pFileInfo->dwFileVersionMS),
                HIWORD(pFileInfo->dwFileVersionLS)
            );
            versionName = CT2A(strVersion.GetString());
        }else{
            versionName = "1.0.0";
        }
    }
    result->Success(flutter::EncodableValue(versionName));
  } else if (method_call.method_name().compare("getVersionCode") == 0) {
    wchar_t szAppFullPath[_MAX_PATH] = {0};
    GetModuleFileName(NULL, szAppFullPath, MAX_PATH);
    DWORD dwLen = GetFileVersionInfoSize(szAppFullPath, NULL);
    char *pszAppVersion = new char[dwLen + 1];
    std::string versionCode = "0";
    if(pszAppVersion){
        memset(pszAppVersion, 0, sizeof(char)*(dwLen + 1));
        GetFileVersionInfo(szAppFullPath, NULL, dwLen, pszAppVersion);
        CString strVersion;
        UINT nLen(0);
        VS_FIXEDFILEINFO *pFileInfo(NULL);
        VerQueryValue(pszAppVersion, TEXT("\\"), (LPVOID*)&pFileInfo, &nLen);
        if(pFileInfo || true){
            strVersion.Format(TEXT("%d"),LOWORD(pFileInfo->dwFileVersionLS));
            versionCode = CT2A(strVersion.GetString());
        }else{
            versionCode = "0";
        }
    }
    result->Success(flutter::EncodableValue(versionCode));
  } else if (method_call.method_name().compare("getChannelInfo") == 0) {
    auto channel_info_map = flutter::EncodableMap{};
    channel_info_map[flutter::EncodableValue("first_install_channel")] = flutter::EncodableValue("Windows");
    channel_info_map[flutter::EncodableValue("current_install_channel")] = flutter::EncodableValue("Windows");
    result->Success(flutter::EncodableValue(channel_info_map));
  } else if (method_call.method_name().compare("openAppStoreComment") == 0) {
    result->Success();
  } else if (method_call.method_name().compare("systemSettingPage") == 0) {
    result->Success();
  } else if (method_call.method_name().compare("minimizeWindow") == 0) {
    HWND hwnd = GetActiveWindow();
    PostMessage(hwnd, WM_SYSCOMMAND, SC_MINIMIZE, 0);
    result->Success();
  } else if (method_call.method_name().compare("killApp") == 0) {
    HWND hwnd = GetActiveWindow();
    PostMessage(hwnd, WM_SYSCOMMAND, SC_CLOSE, 0);
    result->Success();
  } else if (method_call.method_name().compare("launchNoNetwork") == 0) {
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
