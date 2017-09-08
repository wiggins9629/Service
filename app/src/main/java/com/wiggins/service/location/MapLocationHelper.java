package com.wiggins.service.location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wiggins.service.app.MyApplication;

/**
 * @Description 百度定位帮助类
 * @Author 一花一世界
 */
public class MapLocationHelper {

    private Context context;
    private String province;// 省
    private String city;// 市
    private String county;// 区/县
    private double longitude;// 经度
    private double latitude;// 纬度
    private String address;// 地址
    private boolean isLocation = false;// 是否定位
    private short mSpanTime = 2 * 1000;// 定位间隔
    private LocationClient mLocationClient = null;
    private MyBDLocationListener mMyBDLocationListener = null;// 接受百度地图定位的回调类
    private OnLocationListener mLocationListener = null;// 自定义的定位回调接口, 主要是对百度地图定位的封装
    private static MapLocationHelper instance = null;

    public MapLocationHelper() {
        this.context = MyApplication.getContext();
    }

    public static MapLocationHelper getInstance() {
        if (instance == null) {
            synchronized (MapLocationHelper.class) {
                if (instance == null) {
                    instance = new MapLocationHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 启动定位
     */
    public void startLocation() {
        isLocation = true;
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context);
        }
        if (mMyBDLocationListener == null) {
            mMyBDLocationListener = new MyBDLocationListener();
        }
        // 注册监听函数,当没有注册监听函数时，无法发起网络请求
        mLocationClient.registerLocationListener(mMyBDLocationListener);
        // LocationClientOption 该类用来设置定位SDK的定位方式 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setAddrType("all");// 返回的定位结果包含地址信息
        option.disableCache(false);// 禁止启用缓存定位
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(mSpanTime);// 设置发起定位请求的间隔时间：单位ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // start：启动定位SDK stop：关闭定位SDK 调用stop之后，设置的参数LocationClientOption仍然保留
        mLocationClient.start();
        mLocationClient.requestLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            if (mMyBDLocationListener != null) {
                mLocationClient.unRegisterLocationListener(mMyBDLocationListener);
            }
            mLocationClient.stop();
            mLocationClient = null;
            isLocation = false;
        } else {
            isLocation = false;
        }
    }

    /**
     * 实现百度地图的接口
     */
    public class MyBDLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if (mLocationListener != null) {
                    mLocationListener.onReceiveLocation(location);
                }
                isLocation = true;

                // 获得当前定位信息
                StringBuffer sb = new StringBuffer();
                province = location.getProvince();// 省
                city = location.getCity();// 市
                county = location.getDistrict();// 区/县
                longitude = location.getLongitude();// 经度
                latitude = location.getLatitude();// 纬度
                address = location.getAddrStr();// 详细地址

                sb.append("省：" + province + "\n");
                sb.append("市：" + city + "\n");
                sb.append("区/县：" + county + "\n");
                sb.append("经度：" + longitude + "\n");
                sb.append("纬度：" + latitude + "\n");
                sb.append("详细地址：" + address);

                //LogUtil.e("定位信息-经度->" + longitude + "  纬度->" + latitude);
            } else {
                stopLocation();
            }
        }
    }

    /**
     * 经度
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 纬度
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 是否定位
     */
    public boolean isLocation() {
        return isLocation;
    }

    /**
     * 定位间隔时间
     */
    public void setmSpanTime(short mSpanTime) {
        this.mSpanTime = mSpanTime;
    }

    /**
     * 手动触发一次定位请求
     */
    public void requestLocClick() {
        mLocationClient.requestLocation();
    }

    /**
     * 设置自定义的监听器
     */
    public void setOnLocationListener(OnLocationListener locationListener) {
        this.mLocationListener = locationListener;
    }

    public interface OnLocationListener {
        void onReceiveLocation(BDLocation location);
    }
}