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

public class LightUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText lid;
    private EditText lname;
    private EditText cid;
    private EditText state;
    private Button lightSubmitUpdate;
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
        setContentView(R.layout.activity_light_update);

        lid = findViewById(R.id.light_lid_update);
        lname = findViewById(R.id.light_lname_update);
        cid = findViewById(R.id.light_cid_update);
        state = findViewById(R.id.light_state_update);
        lightSubmitUpdate = findViewById(R.id.light_submit_update);
        backMainActivity = findViewById(R.id.light_back_main_update);
        lightSubmitUpdate.setOnClickListener(this);
        backMainActivity.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.light_submit_update:
                updateLight();
                break;
            case R.id.light_back_main_update:
                Intent intent = new Intent(LightUpdateActivity.this,MainActivity.class);
                startActivity(intent);
        }
    }

    public void updateLight() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String lid1 = lid.getText().toString();
                    String lname1 = lname.getText().toString();
                    String cid1 = cid.getText().toString();
                    String state1 = state.getText().toString();

                    RequestBody requestBody = new FormBody.Builder().add("lid",lid1).add("lname", lname1)
                            .add("cid", cid1).add("state", state1).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/updateLightServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        DataSupport.deleteAll("light");
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
