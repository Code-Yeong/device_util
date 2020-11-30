package com.chivan.device_util.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AppStoreStar {

    public static String TAG = "AppStoreStar";

    private Context mContext;
    private HashMap<String, String> channelMaps = new HashMap<>();

    public AppStoreStar(Context mContext) {
        this.mContext = mContext;
        initChannelData();
    }

    public void toStoreForStar(String channelNumber, String packageName) {
//        channelNumber = "yyb";
        //引导用户去评价
        try {
            if (packageName == null || packageName.isEmpty()) {
                //如果没有传packageName,则表示要在app store中打开当前App
                packageName = mContext.getPackageName();
            }
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String getDownloadStore = getAppInstalledName(channelNumber);
            Log.i(TAG, "appStar，packageName:" + getDownloadStore);
            if (!TextUtils.isEmpty(getDownloadStore)) {
                intent.setPackage(getDownloadStore);
            }
            mContext.startActivity(intent);
        } catch (Exception e) {
            // 没有任何商店的时候，谈toast
            Toast.makeText(mContext, "没有找到应用商店哦", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getAppInstalledName(String channelNumber) {

        String packageName = channelMaps.get(channelNumber);
        if (isPackageInstalled(packageName)) {
            return packageName;
        } else {
            String brand = DeviceUtils.getBrand();
            if (brand.equalsIgnoreCase("huawei")) {
                packageName = channelMaps.get("huawei");
            } else if (brand.equalsIgnoreCase("xiaomi")) {
                packageName = channelMaps.get("xiaomi");
            } else if (brand.equalsIgnoreCase("oppo")) {
                packageName = channelMaps.get("oppo");
            } else if (brand.equalsIgnoreCase("vivo")) {
                packageName = channelMaps.get("vivo_store");
            } else if (brand.equalsIgnoreCase("meizu")) {
                packageName = channelMaps.get("meizu");
            } else if (brand.equalsIgnoreCase("samsang")) {
                packageName = channelMaps.get("samsang");
            }
            if (!TextUtils.isEmpty(packageName) && isPackageInstalled(packageName)) {
                return packageName;
            }
            Iterator iterator = channelMaps.keySet().iterator();
            while (iterator.hasNext()) {
                packageName = channelMaps.get(iterator.next().toString());
                if (isPackageInstalled(packageName)) {
                    return packageName;
                }
            }
            return "";
        }
    }

    public boolean isPackageInstalled(String packageName) {
        boolean isInstalled = false;
        if (TextUtils.isEmpty(packageName))
            return isInstalled;
        PackageManager pm = mContext.getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);

        for (int i = 0; i < installedPkgs.size(); i++) {
            String installPkg = "";
            PackageInfo packageInfo = installedPkgs.get(i);
            try {
                installPkg = packageInfo.packageName;

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(installPkg))
                continue;
            if (installPkg.equals(packageName)) {
                isInstalled = true;
                break;
            }

        }
        return isInstalled;
    }

    private void initChannelData() {
        channelMaps.put("vivo_store", "com.bbk.appstore");//vivo
        channelMaps.put("oppo", "com.oppo.market");//oppo
        channelMaps.put("meizu", "com.meizu.mstore");//meizu
        channelMaps.put("huawei", "com.huawei.appmarket");//huawei
        channelMaps.put("xiaomi", "com.xiaomi.market");//xiaomi
        channelMaps.put("360_store", "com.qihoo.appstore");//360
        channelMaps.put("wandoujia", "com.wandoujia.phoenix2");//wandoujia
        channelMaps.put("yyb", "com.tencent.android.qqdownloader");//yyb
//        channelMaps.put("Android_APP","com.pp.assistant");//pp
        channelMaps.put("baidu", "com.baidu.appsearch");//baidu
//        channelMaps.put("","com.dragon.android.pandaspace");//91
        channelMaps.put("anzhi_store", "com.hiapk.marketpho");//安智
        channelMaps.put("appchina", "com.yingyonghui.market");//yingyonghui
        channelMaps.put("lenovo", "com.lenovo.leos.appstore");//lenovo
        channelMaps.put("gionee", "com.gionee.aora.market");//金立
        channelMaps.put("sougou", "com.sogou.androidtool");//sogou
        channelMaps.put("samsang", "com.sec.android.app.samsungapps");//三星
        channelMaps.put("coolpad", "com.yulong.android.coolmart");//酷派
        channelMaps.put("gfan", "com.mappn.gfan");//机锋
//        channelMaps.put("uc_store", "com.UCMobile");//UC浏览器
//        channelMaps.put("mqqbrowser", "com.tencent.mtt");//QQ浏览器
        channelMaps.put("letv", "com.letv.app.appstore");//乐视
//        channelMaps.put("10028434", "com.android.vending");//Google
    }
}
