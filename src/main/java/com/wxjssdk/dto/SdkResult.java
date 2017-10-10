package com.wxjssdk.dto;

/**
 * Created by huangfei on 12/05/2017.
 */
public class SdkResult<T> {

    private boolean success;

    private T data;

    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
