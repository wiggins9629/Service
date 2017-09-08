package com.wiggins.service.socket;

import android.util.Log;

import com.wiggins.service.utils.LogUtil;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

import static android.content.ContentValues.TAG;

/**
 * @Description WebSocket连接
 * @Author 一花一世界
 */
public class WebSocket {

    private static WebSocket instance = null;
    private WebSocketConnection mConnect;
    private static final String wsUri = "ws://112.74.43.157:3002";
    private String mMsg = "";

    private WebSocket() {
        webSocketConnect(false);
    }

    public static WebSocket getInstance() {
        if (instance == null) {
            synchronized (WebSocket.class) {
                if (instance == null) {
                    instance = new WebSocket();
                }
            }
        }
        return instance;
    }

    /**
     * WebSocket连接，接收服务器消息
     */
    private void webSocketConnect(boolean isMsg) {
        if (mConnect == null) {
            mConnect = new WebSocketConnection();
        }
        try {
            mConnect.connect(wsUri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    super.onOpen();
                    // 连接启动时回调
                    LogUtil.e("Connect To: " + wsUri);
                }

                @Override
                public void onTextMessage(String payload) {
                    super.onTextMessage(payload);
                    // 接收到消息后回调
                    LogUtil.e("接收消息：" + payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    super.onClose(code, reason);
                    // 关闭连接时候的回调
                    webSocketConnect(false);
                    Log.i(TAG, "Connect Close......");
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
        if (isMsg) {
            sendMessage(mMsg);
        }
    }

    /**
     * 发送消息
     */
    public void sendMessage(String msg) {
        this.mMsg = msg;
        LogUtil.e("发送消息内容：" + msg);
        if (mConnect != null && mConnect.isConnected()) {
            mConnect.sendTextMessage(msg);
        } else {
            webSocketConnect(true);
        }
    }

    /**
     * 断开连接
     */
    public void closeConnect() {
        if (mConnect != null && mConnect.isConnected()) {
            mConnect.disconnect();
            mConnect = null;
        }
    }
}