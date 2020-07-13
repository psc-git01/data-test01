package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ClazzAddActivity extends AppCompatActivity implements View.OnClickListener {
    
    private EditText fid;
    private EditText clazzCode;
    private Button clazzSubmit;
    private Button backMainActivity;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;

    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SUCCESS:
                    Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                    break;
                case DEFEATE:
                    Toast.makeText(getApplicationContext(), "添加失败，请重新添加", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz_add);

        
        fid = findViewById(R.id.clazz_fid);
        clazzCode = findViewById(R.id.clazz_code);
        clazzSubmit = findViewById(R.id.clazz_submit);
        backMainActivity = findViewById(R.id.clazz_back_main);
        clazzSubmit.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clazz_submit:
                addClazz();
                break;
            case R.id.clazz_back_main:
                Intent intent = new Intent(ClazzAddActivity.this, MainActivity.class);
                startActivity(intent);

        }
    }

    public void addClazz() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String fid1 = fid.getText().toString();
                    String clazzCode1 = clazzCode.getText().toString();
                    Log.d("res", "zzzzzzzzzzzzzzzzzzzzz");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("fid", fid1).add("classCode", clazzCode1).build();
                    Request request = new Request.Builder().url("10.0.2.2:8080/APPServer/addClazzServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();

                    Message message = new Message();
                    if (s.equals("1")) {
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        DataSupport.deleteAll("clazz");
                        message.what = SUCCESS;
                        handler.sendMessage(message);
                    } else {
                        message.what = DEFEATE;
                        handler.sendMessage(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
