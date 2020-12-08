package com.chivan.device_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.chivan.device_util.utils.AppStoreStar;
import com.chivan.device_util.utils.AssetsUtils;
import com.chivan.device_util.utils.PermissionUtils;
import com.chivan.device_util.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * DeviceUtilPlugin
 */
public class DeviceUtilPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private final static String CHANNEL_NAME = "device_util";
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity

    private MethodChannel channel;
    private Activity mActivity;
    private Context mContext;

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(new DeviceUtilPlugin());
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL_NAME);
        channel.setMethodCallHandler(this);
        mContext = flutterPluginBinding.getApplicationContext();
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        mContext = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        mActivity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        mActivity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        mActivity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        mActivity = null;
    }


    private Map<String, String> getChannelInfo() {
        Map<String, String> channelInfo = new HashMap<>(2);
        String defaultChannelStr = AssetsUtils.readText(mContext, "channel_default.ini");
        String channelStr = AssetsUtils.readText(mContext, "channel.ini");
        if (!channelStr.isEmpty()) {
            defaultChannelStr = channelStr;
        }
        String latestChannel = "unknown";
        if (!defaultChannelStr.isEmpty()) {
            String[] temp = defaultChannelStr.split("=");
            int expectedLength = 2;
            if (temp.length == expectedLength) {
                latestChannel = temp[1];
            }
        }

        String key = "FIRST_INSTALL_CHANNEL";
        String defaultValue = "unknown";
        String lastChannel = PreferenceUtils.getString(mContext, key, defaultValue);
        if (lastChannel == null || lastChannel.isEmpty() || defaultValue.equals(lastChannel)) {
            PreferenceUtils.putString(mContext, key, latestChannel);
        }
        channelInfo.put("first_install_channel", lastChannel);
        channelInfo.put("current_install_channel", latestChannel);
        return channelInfo;
    }

    private PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (ConstantValue.GET_PLATFORM_VERSION.equals(call.method)) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (ConstantValue.GET_VERSION_CODE.equals(call.method)) {
            PackageInfo pi = getPackageInfo(mContext);
            if (pi == null) {
                result.success("");
            } else {
                result.success(String.valueOf(pi.versionCode));
            }
        } else if (ConstantValue.GET_VERSION_NAME.equals(call.method)) {
            PackageInfo pi = getPackageInfo(mContext);
            if (pi == null) {
                result.success("");
            } else {
                result.success(pi.versionName);
            }
        } else if (ConstantValue.GET_CHANNEL_INFO.equals(call.method)) {
            result.success(getChannelInfo());
        } else if (ConstantValue.OPEN_MARKET_COMMENT.equals(call.method)) {
            AppStoreStar appStoreStar = new AppStoreStar(mContext);
            Map<String, String> channelInfo = getChannelInfo();
            appStoreStar.toStoreForStar(channelInfo.get("current_install_channel"), null);
        } else if (ConstantValue.SYSTEM_SETTING_PAGE.equals(call.method)) {
            try {
                PermissionUtils.goToSetting(mActivity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ConstantValue.LAUNCH_NO_NETWORK.equals(call.method)) {
            try {
                mContext.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ConstantValue.KILL_APP.equals(call.method)) {
            System.exit(0);
        } else {
            result.notImplemented();
        }
    }


}
