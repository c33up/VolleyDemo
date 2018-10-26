package com.css.volleytest.volley;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.css.volleytest.BaseApplication;

import java.util.Iterator;
import java.util.Map;


public class NetworkHelper {

    private static NetworkHelper instance;
    private static final int CONNECT_TIMEOUT=5*1000;//连接超时
    private static final int CONNECT_TIME=0;//连接次数

    //单例获取工具类
    public static NetworkHelper getInstance() {
        if (instance == null) {
            synchronized (NetworkHelper.class) {
                if (instance == null) {
                    instance = new NetworkHelper();
                }
            }
        }
        return instance;
    }

    //获取请求队列
    private RequestQueue mRequestQueue = BaseApplication.sRequestQueue;


    /**
     * @param activity 当前Activity Context
     * @param url      请求地址
     * @param map      请求参数
     * @param callBackListener  返回结果回调处理
     */
    public void post(final Activity activity, String url, final Map<String, String> map,final OnCallBackListener callBackListener) {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (callBackListener!=null){
                    callBackListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBackListener!=null){
                    callBackListener.onFail(volleyError.getMessage());
                }
                showErrorMessage(activity, volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                CONNECT_TIMEOUT,//链接超时时间
                CONNECT_TIME,//重新尝试连接次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT//曲线增长因子
        ));
        mRequestQueue.add(request);
    }

    public void get(final Activity activity, String url, Map<String, String> map, final OnCallBackListener callBackListener) {

        String getUrl="";
        if (map == null) {
            getUrl = url;
        } else {
            StringBuffer sb = new StringBuffer(url);
            sb.append("?");
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                sb.append(k + "=" + v + "&");
            }
            getUrl = sb.toString().substring(0, sb.toString().length() - 1);
        }

        StringRequest request = new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (callBackListener!=null){
                    callBackListener.onSuccess(s);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBackListener!=null){
                    callBackListener.onFail(volleyError.getMessage());
                }
                showErrorMessage(activity, volleyError);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                CONNECT_TIMEOUT,//链接超时时间
                CONNECT_TIME,//重新尝试连接次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(request);
    }


    public <T> void doHttpGet(String url, Map<String, String> map, String tag,
                              final OnCallBackListener<T> callBackListener,Class<T> clazz) {

        String getUrl="";
        if (map == null) {
            getUrl = url;
        } else {
            StringBuffer sb = new StringBuffer(url);
            sb.append("?");
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                Object v = entry.getValue();
                sb.append(k + "=" + v + "&");
            }
            getUrl = sb.toString().substring(0, sb.toString().length() - 1);
        }

        VolleyRequest request = new VolleyRequest(Request.Method.GET, getUrl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callBackListener!=null){
                    callBackListener.onFail(volleyError.getMessage());
                }
            }
        }, new Response.Listener<T>() {
            @Override
            public void onResponse(T s) {
                if (callBackListener!=null){
                    callBackListener.onSuccess(s);
                }
            }
        },clazz);
        request.setTag(tag);
        mRequestQueue.add(request);
    }


    private void showErrorMessage(Activity context, VolleyError error) {
        if (error instanceof NoConnectionError || error instanceof NetworkError) {
            showToast(context, "网络链接异常");
        } else if (error instanceof TimeoutError) {
            showToast(context, "连接超时");
        } else if (error instanceof AuthFailureError) {
            showToast(context, "身份验证失败！");
        } else if (error instanceof ParseError) {
            showToast(context, "解析错误！");
        } else if (error instanceof ServerError) {
            showToast(context, "服务器响应错误！");
        }
    }

    private void showToast(Activity context, String str) {
        if (!context.isFinishing()) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    }
}
