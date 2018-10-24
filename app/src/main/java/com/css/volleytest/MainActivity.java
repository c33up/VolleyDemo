package com.css.volleytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.css.volleytest.volley.NetworkUtil;
import com.css.volleytest.volley.OnCallBackListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.edit_url);
        button=findViewById(R.id.btn_get);
        textView=findViewById(R.id.tv_content);
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
        String url=editText.getText().toString();
        NetworkUtil.getInstance().get(MainActivity.this,
                url,
                null,
                new OnCallBackListener() {
            @Override
            public void onSuccess(Object result) {
                textView.setText(result.toString());
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
    }
}
