package com.css.volleytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.css.volleytest.bean.BaseBean;
import com.css.volleytest.volley.NetworkHelper;
import com.css.volleytest.volley.OnCallBackListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.tv_content);

        button=findViewById(R.id.btn_get);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                getHttp();
                break;
        }
    }

    private void getHttp(){
        Map<String,String> map=new HashMap<>();
        map.put("citykey","101010100");
        NetworkHelper.getInstance().doHttpGet(
                "http://wthrcdn.etouch.cn/weather_mini",
                map,"get",
                new OnCallBackListener<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean result) {

                        textView.setText("status:"+result.getStatus());
                    }

                    @Override
                    public void onFail(String errorMsg) {

                    }
                },BaseBean.class);

//        NetworkHelper.getInstance().get(MainActivity.this,
//                url,
//                null,
//                new OnCallBackListener() {
//            @Override
//            public void onSuccess(Object result) {
//                textView.setText(result.toString());
//            }
//
//            @Override
//            public void onFail(String errorMsg) {
//
//            }
//        });
    }
}
