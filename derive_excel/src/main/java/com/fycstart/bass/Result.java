package com.fycstart.bass;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22上午 10:52
 */
public class Result<T> {

    private T data;

    private Integer code;

    private String msg;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
