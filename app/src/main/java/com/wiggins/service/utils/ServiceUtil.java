package com.wiggins.service.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

import java.util.List;

/**
 * @Description 服务工具类
 * @Author 一花一世界
 */
public class ServiceUtil {

    /**
     * 判断某个服务是否正在运行
     *
     * @param serviceName 是包名+服务的类名
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> mList = manager.getRunningServices(Integer.MAX_VALUE);
        if (mList.size() <= 0) {
            return false;
        }
        for (RunningServiceInfo service : mList) {
            String mName = service.service.getClassName();
            if (serviceName.equals(mName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断进程是否运行
     *
     * @param processName 进程名称
     */
    public static boolean isProcessRunning(Context context, String processName) {
        boolean isRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> mList = manager.getRunningAppProcesses();
        for (RunningAppProcessInfo info : mList) {
            if (info.processName.equals(processName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }
}
