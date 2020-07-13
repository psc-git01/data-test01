package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class TowerOffLightActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tid;

    private Button towerOffLight;
    private Button backMainActivity;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;

    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case SUCCESS:
                    Toast.makeText(getApplicationContext(), "全部关闭成功", Toast.LENGTH_SHORT).show();
                    break;
                case DEFEATE:
                    Toast.makeText(getApplicationContext(), "全部关闭失败，请重新关闭", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_off_light);

        tid = findViewById(R.id.tower_tid_offlight);

        towerOffLight = findViewById(R.id.tower_submit_offlight);
        backMainActivity = findViewById(R.id.tower_back_main_offlight);
        towerOffLight.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tower_submit_offlight:
                offAllLightTower();
                break;
            case R.id.tower_back_main_offlight:
                Intent intent = new Intent(TowerOffLightActivity.this, MainActivity.class);
                startActivity(intent);
        }
    }

    public void offAllLightTower() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String tid1 = tid.getText().toString();


                    RequestBody requestBody = new FormBody.Builder().add("tid", tid1).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/offAllLightTowerServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();

                    Message message = new Message();
                    if ( s.compareTo("0")>=0) {
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);

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