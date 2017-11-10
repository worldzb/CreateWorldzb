package com.example.demo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {
    public OkHttpClient okHttpClient=new OkHttpClient();
    private static final String TGA="yang";
    public TextView tv;
    private ListView listView;

    //组件定义
    private EditText input;
    //private TextView show;
    private Button btn;

    private Button btn1,btn2;
    private Handler innerHandler;

    private String[] data=new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Log.d(TGA,"the activity is create");
        this.input=(EditText)findViewById(R.id.input);
        this.btn=(Button)findViewById(R.id.btn);
        //this.show=(TextView)findViewById(R.id.show);
        /**
         * 线程消息传递
         */
        this.btn1=(Button) findViewById(R.id.btn1);
        this.btn2=(Button) findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        innerHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                String currentThreadName=Thread.currentThread().getName();
                String data=(String) msg.obj;
                Log.d(TGA,currentThreadName+":"+data);
                return false;
            }
        });

        //list view 初始化
        listView=(ListView)findViewById(R.id.listView);
        for (int i=0;i<10;i++){
            data[i]="这是第"+i+"条数据";
        }
        ArrayAdapter<String> arrAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                Log.d(TGA,"btn1");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String currentThreadName=Thread.currentThread().getName();
                        String data="这是内部类";
                        //Log.d(TGA,currentThreadName+":"+data);

                        Message message=new Message();
                        message.what=1;
                        message.obj=data;
                        innerHandler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.btn2:
                Log.d(TGA,"btn2");
                break;
        }
    }










    /**
     * 发送get请求并得到返回的结果
     * @param view
     * @throws IOException
     */
    public void doGet(View view) throws IOException {

        //1,创建OK HTTP对象
        OkHttpClient okHttpClient=new OkHttpClient();
        //2，构造 request
        Request.Builder builder=new Request.Builder();
        Log.d(TGA,"创建了http请求");
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
                final String str = response.body().string();
                //L.e(str);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(str);
                    }
                });

            }
        });
       Log.d(TGA,"按钮被点击了！");
    }

    public void showInput(View view){
        //this.show.setText(this.input.getText());
    }


}
