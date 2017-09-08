package com.wiggins.service.bean;

/**
 * @Description 传递格式
 * @Author 一花一世界
 */
public class Format {

    private int code;
    private Locate data;

    public Format(int code, Locate data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Locate getData() {
        return data;
    }

    public void setData(Locate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Format{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
