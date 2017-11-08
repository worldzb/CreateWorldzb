package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }
    public void doGet(View view) throws IOException {
        //1,创建OK HTTP对象
        OkHttpClient okHttpClient=new OkHttpClient();
        //2，构造 request
        Request.Builder builder=new Request.Builder();

        //3将request 封装 为 call
        Request request = builder.get().url("http://www.worldzb.cn/").build();
        okhttp3.Call call=okHttpClient.newCall(request);

        //4，执行call
        //Response response = call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                L.e("OnFailure:"+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                L.e("onResponse:");
                String str = response.body().string();
                L.e(str);
            }
        });
    }
}
