package com.chivan.device_util.utils;

/**
 * 跳转设置页面
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class PermissionUtils {
    public static String TAG = "PermissionUtils";
    /**
     * Build.MANUFACTURER
     */
    private static final String MANUFACTURER_HUAWEI = "Huawei";//华为
    private static final String MANUFACTURER_MEIZU = "Meizu";//魅族
    private static final String MANUFACTURER_XIAOMI = "Xiaomi";//小米
    private static final String MANUFACTURER_SONY = "Sony";//索尼
    private static final String MANUFACTURER_OPPO = "OPPO";
    private static final String MANUFACTURER_LG = "LG";
    private static final String MANUFACTURER_VIVO = "vivo";
    private static final String MANUFACTURER_SAMSUNG = "samsung";//三星
    private static final String MANUFACTURER_LETV = "Letv";//乐视
    private static final String MANUFACTURER_ZTE = "ZTE";//中兴
    private static final String MANUFACTURER_YULONG = "YuLong";//酷派
    private static final String MANUFACTURER_LENOVO = "LENOVO";//联想

    /**
     * 此函数可以自己定义
     *
     * @param activity
     */
    public static void goToSetting(Activity activity) {
        switch (Build.MANUFACTURER) {
            case MANUFACTURER_HUAWEI:
                Huawei(activity);
                break;
            case MANUFACTURER_MEIZU:
                Meizu(activity);
                break;
            case MANUFACTURER_XIAOMI:
                Xiaomi(activity);
                break;
            case MANUFACTURER_SONY:
                Sony(activity);
                break;
            case MANUFACTURER_OPPO:
                OPPO(activity);
                break;
            case MANUFACTURER_LG:
                LG(activity);
                break;
            case MANUFACTURER_LETV:
                Letv(activity);
                break;
            default:
                SystemConfig(activity);
                Log.e(TAG, "goToSetting, 目前暂不支持此系统");
                break;
        }
    }

    private static void Huawei(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {

                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void Meizu(Activity activity) {
        try {

            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", activity.getPackageName());
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void Xiaomi(Activity activity) {
        try {
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", activity.getPackageName());
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {

                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void Sony(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void OPPO(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void LG(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static void Letv(Activity activity) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity
     */
    private static void _360(Activity activity) {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", activity.getPackageName());
            ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            try {
                SystemConfig(activity);
            } catch (Exception e2) {
                Toast.makeText(activity, "您的机型暂不支持跳转，请手动设置", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    private static void ApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    private static void SystemConfig(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        activity.startActivity(intent);
    }
}

