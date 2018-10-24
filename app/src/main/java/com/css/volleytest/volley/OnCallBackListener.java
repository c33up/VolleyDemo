package com.css.volleytest.volley;

public interface OnCallBackListener<T> {
    void onSuccess(T result);
    void onFail(String errorMsg);
}
