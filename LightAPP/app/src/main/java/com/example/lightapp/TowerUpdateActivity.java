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

public class TowerUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText tid;
    private EditText tname;
    private EditText address;
    private EditText towerCode;
    private Button towerSubmitUpdate;
    private Button backMainActivity;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;

    Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case SUCCESS:
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    break;
                case DEFEATE:
                    Toast.makeText(getApplicationContext(),"修改失败，请重新修改",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_update);
        tid = findViewById(R.id.tower_tid_update);
        tname = findViewById(R.id.tower_tname_update);
        address = findViewById(R.id.tower_address_update);
        towerCode = findViewById(R.id.tower_code_update);
        towerSubmitUpdate = findViewById(R.id.tower_submit_update);
        backMainActivity = findViewById(R.id.tower_back_main_update);
        towerSubmitUpdate.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tower_submit_update:
                updateTower();
                break;
            case R.id.tower_back_main_update:
                Intent intent = new Intent(TowerUpdateActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }
    public void updateTower() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String tid1 = tid.getText().toString();
                    String tname1 = tname.getText().toString();
                    String address1 = address.getText().toString();
                    String towerCode1 = towerCode.getText().toString();
                    Log.d("res","zzzzzzzzzzzzzzzzzzzzz");
                    RequestBody requestBody = new FormBody.Builder().add("tid",tid1).add("tname", tname1)
                            .add("address", address1).add("towerCode", towerCode1).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/updateTowerServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        DataSupport.deleteAll("tower");
                        message.what = SUCCESS;
                        handler.sendMessage(message);
                    }else {
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
