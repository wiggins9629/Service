package com.wiggins.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wiggins.service.base.BaseActivity;
import com.wiggins.service.service.LocateService;
import com.wiggins.service.utils.Constant;
import com.wiggins.service.utils.ServiceUtil;
import com.wiggins.service.utils.ToastUtil;

/**
 * @Description Service+WebSocket实现实时定位
 * @Author 一花一世界
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnRunning = (Button) findViewById(R.id.btn_running);
    }

    private void setListener() {
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnRunning.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                if (!isServiceRunning()) {
                    Intent startIntent = new Intent(this, LocateService.class);
                    startService(startIntent);
                }
                break;
            case R.id.btn_stop:
                if (isServiceRunning()) {
                    Intent stopIntent = new Intent(this, LocateService.class);
                    stopService(stopIntent);
                }
                break;
            case R.id.btn_running:
                if (isServiceRunning()) {
                    ToastUtil.showText("正在运行");
                } else {
                    ToastUtil.showText("停止运行");
                }
                break;
        }
    }

    /**
     * 服务是否运行
     */
    private boolean isServiceRunning() {
        return ServiceUtil.isServiceRunning(this, Constant.SERVICE_NAME);
    }
}