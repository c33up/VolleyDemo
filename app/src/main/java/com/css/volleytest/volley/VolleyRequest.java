package com.css.volleytest.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class VolleyRequest<T> extends Request<T> {
    private static final int CONNECT_TIMEOUT=5*1000;//连接超时
    private static final int CONNECT_TIME=0;//连接次数
    private Class<T> clazz;
    private Response.Listener<T> mListener;

    public VolleyRequest(int method, String url, Response.ErrorListener listener,
                         Response.Listener<T> mListener, Class<T> clazz) {
        super(method, url, listener);
        this.mListener=mListener;
        this.clazz=clazz;
        setRetryPolicy(new DefaultRetryPolicy(
                CONNECT_TIMEOUT,//链接超时时间
                CONNECT_TIME,//重新尝试连接次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
    }



    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //NetworkResponse 这个类中包含了服务器返回的结果，返回头等。其中的 response.data 这个是一个 byte 数组
        //response.headers 这个是请求头数据
        // HttpHeaderParser.parseCharset(response.headers) 这个就是服务器返回的编码方式
        String json;
        try {
                json=new String(response.data, "UTF-8");
//            json=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            json=new String(response.data);
        }
        T obj;
        try {
            obj=JsonHelper.parseObject(json,clazz);
            return Response.success(obj,HttpHeaderParser.parseCacheHeaders(response));
        }catch (Exception e) {
            return Response.error(new ParseError());
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
