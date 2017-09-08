package com.wiggins.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.google.gson.Gson;
import com.wiggins.service.bean.Format;
import com.wiggins.service.bean.Locate;
import com.wiggins.service.location.MapLocationHelper;
import com.wiggins.service.socket.WebSocket;
import com.wiggins.service.sp.SharedPreferencesHelper;
import com.wiggins.service.utils.Constant;
import com.wiggins.service.utils.LogUtil;
import com.wiggins.service.utils.ServiceUtil;
import com.wiggins.service.utils.ToastUtil;

/**
 * @Description 定位服务
 * @Author 一花一世界
 */
public class LocateService extends Service {

    private boolean isRun = true;
    private MapLocationHelper location;
    private SharedPreferencesHelper sp;
    private WebSocket socket;
    private Gson mGson;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initData();
        return START_STICKY;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        isRun = true;
        location = MapLocationHelper.getInstance();
        sp = SharedPreferencesHelper.getInstance();
        socket = WebSocket.getInstance();
        if (mGson == null) {
            mGson = new Gson();
        }
        thread.start();
    }

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isRun) {
                try {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (location.isLocation() == false) {
                        location.startLocation();
                    }

                    LogUtil.e("经度：" + sp.get("longitude", "") + "   纬度：" + sp.get("latitude", ""));
                    String longitude = String.valueOf(location.getLongitude());
                    String latitude = String.valueOf(location.getLatitude());
                    if (location.getLongitude() != 0.0 && location.getLatitude() != 0.0) {
                        if (!sp.get("longitude", "").equals(longitude)
                                || !sp.get("latitude", "").equals(latitude)) {

                            sp.put("longitude", longitude);
                            sp.put("latitude", latitude);

                            String str = mGson.toJson(new Format(200,
                                    new Locate(longitude, latitude)));
                            socket.sendMessage(str);
                        }
                    }

                    ToastUtil.showText("经度:" + longitude + "  纬度：" + latitude);
                    break;
            }
        }
    };

    /**
     * 重启服务
     */
    private void restartService() {
        if (!ServiceUtil.isServiceRunning(this, Constant.SERVICE_NAME)) {
            isRun = false;
            MapLocationHelper.getInstance().stopLocation();
            WebSocket.getInstance().closeConnect();
            SharedPreferencesHelper.getInstance().clear();
            Intent intent = new Intent(this, LocateService.class);
            this.startService(intent);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        restartService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        restartService();
    }
}