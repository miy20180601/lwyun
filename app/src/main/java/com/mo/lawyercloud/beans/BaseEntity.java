package com.mo.lawyercloud.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohaifeng on 2017/6/7.
 */

public class BaseEntity<E> {

    @SerializedName("result")
    private int result;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private E data;


    public boolean isSuccess() {
        return result == 1;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

}