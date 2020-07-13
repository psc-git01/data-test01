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

public class ClazzUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText cid;
    
    private EditText fid;
    private EditText clazzCode;
    private Button clazzSubmitUpdate;
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
        setContentView(R.layout.activity_clazz_update);

        cid = findViewById(R.id.clazz_cid_update);
        
        fid = findViewById(R.id.clazz_fid_update);
        clazzCode = findViewById(R.id.clazz_code_update);
        clazzSubmitUpdate = findViewById(R.id.clazz_submit_update);
        backMainActivity = findViewById(R.id.clazz_back_main_update);
        clazzSubmitUpdate.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clazz_submit_update:
                updateClazz();
                break;
            case R.id.clazz_back_main_update:
                Intent intent = new Intent(ClazzUpdateActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }

    public void updateClazz() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String cid1 = cid.getText().toString();

                    String fid1 = fid.getText().toString();
                    String clazzCode1 = clazzCode.getText().toString();

                    RequestBody requestBody = new FormBody.Builder().add("cid",cid1)
                            .add("fid", fid1).add("classCode", clazzCode1).build();
                    Request request = new Request.Builder().url("10.0.2.2:8080/APPServer/updateClazzServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        DataSupport.deleteAll("clazz");
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
