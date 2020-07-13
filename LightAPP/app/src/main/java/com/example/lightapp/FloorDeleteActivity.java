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

public class FloorDeleteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fname;
    private EditText floorCode;
    private Button floorDelete;
    private Button backMainActivity;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;

    Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case SUCCESS:
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    break;
                case DEFEATE:
                    Toast.makeText(getApplicationContext(),"删除失败，请重新删除",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_delete);

        fname = findViewById(R.id.floor_fname_delete);
        floorCode = findViewById(R.id.floor_code_delete);
        floorDelete = findViewById(R.id.floor_submit_delete);
        backMainActivity = findViewById(R.id.floor_back_main_delete);
        floorDelete.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floor_submit_delete:
                deleteFloor();
                break;
            case R.id.floor_back_main_delete:
                Intent intent = new Intent(FloorDeleteActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }
    public void deleteFloor() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String fname1 = fname.getText().toString();
                    String floorCode1 = floorCode.getText().toString();

                    RequestBody requestBody = new FormBody.Builder().add("fname", fname1)
                            .add("floorCode", floorCode1).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/deleteFloorServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();

                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        DataSupport.deleteAll("floor");
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
