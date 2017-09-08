package com.wiggins.service.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wiggins.service.app.MyApplication;
import com.wiggins.service.location.MapLocationHelper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @Description SharedPreferences工具类
 * @Author 一花一世界
 */
public class SharedPreferencesHelper {

    // 文件名
    private final String FILE_NAME = "shared_data";
    // 存储后的文件路径：/data/data/<package name>/shares_prefs/文件名.xml
    public static final String PATH = "/data/data/com.wiggins.service/shared_prefs/shared_data.xml";
    private SharedPreferences sp;
    private Editor editor;
    private static SharedPreferencesHelper instance = null;

    private SharedPreferencesHelper() {
        initSP(MyApplication.getContext());
    }

    public static SharedPreferencesHelper getInstance() {
        if (instance == null) {
            synchronized (MapLocationHelper.class) {
                if (instance == null) {
                    instance = new SharedPreferencesHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void initSP(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存数据的方法，根据类型调用不同的保存方法
     *
     * @param key    键
     * @param object 值
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.commit();
    }

    /**
     * 获取保存数据的方法，根据保存数据的类型调用对应方法取值
     *
     * @param key           键
     * @param defaultObject 默认值
     */

    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else {
            return sp.getString(key, null);
        }
    }

    /**
     * 移除SP中某个key对应的数据项
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除SP中所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

    /**
     * 查询SP中包含特定key的数据
     *
     * @param key
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * 获取存储文件的数据
     */
    public String getFileData() {
        StringBuffer buff = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)));
            String str;
            while ((str = reader.readLine()) != null) {
                buff.append(str + "/n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buff.toString();
    }
}