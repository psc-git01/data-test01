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

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private EditText password1;
    private Button submit;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;

    Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case SUCCESS:
                    Toast.makeText(getApplicationContext(),"注册成功，页面跳转",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
                case DEFEATE:
                    Toast.makeText(getApplicationContext(),"注册失败，请重新注册",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.usernameZc);
        password = (EditText)findViewById(R.id.passwordZc);
        password1 = (EditText)findViewById(R.id.passwordZc1);
        submit = findViewById(R.id.submit_register);
        submit.setOnClickListener(this);
    }

    private void sendRequestRegister(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String name = username.getText().toString();
                    String pass = password.getText().toString();
                    RequestBody requestBody = new FormBody.Builder().add("username",name)
                            .add("password",pass).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/registerServlet")
                            .post(requestBody).build();
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();

                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_register:
                if(password.getText().toString().compareTo(password1.getText().toString()) !=0){
                    Toast.makeText(getApplicationContext(),"请确认密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                sendRequestRegister();
                break;

        }
    }
}
