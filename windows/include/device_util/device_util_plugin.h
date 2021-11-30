#ifndef FLUTTER_PLUGIN_DEVICE_UTIL_PLUGIN_H_
#define FLUTTER_PLUGIN_DEVICE_UTIL_PLUGIN_H_

#include <flutter_plugin_registrar.h>

#if defined(__cplusplus)
extern "C" {
#endif

void DeviceUtilPluginRegisterWithRegistrar(
    FlutterDesktopPluginRegistrarRef registrar);

#if defined(__cplusplus)
}  // extern "C"
#endif

#endif  // FLUTTER_PLUGIN_DEVICE_UTIL_PLUGIN_H_
