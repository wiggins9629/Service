package com.wiggins.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.wiggins.service.service.LocateService;
import com.wiggins.service.utils.Constant;
import com.wiggins.service.utils.LogUtil;
import com.wiggins.service.utils.ServiceUtil;

/**
 * @Description 广播监听
 * @Author 一花一世界
 */
public class LocateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("接收广播：" + intent.getAction() + "   服务是否运行：" + ServiceUtil.isServiceRunning(context, Constant.SERVICE_NAME));
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)
                || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                || intent.getAction().equals(Intent.ACTION_TIME_TICK)
                || intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
                || intent.getAction().equals(Intent.ACTION_SCREEN_ON)
                || intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!ServiceUtil.isServiceRunning(context, Constant.SERVICE_NAME)) {
                Intent service = new Intent(context, LocateService.class);
                context.startService(service);
            }
        }
    }
}