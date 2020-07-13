package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.aware.PublishConfig;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public Object requestLight;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private Button register;
    public static final int SUCCESS = 1;
    public static final int DEFEATE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPassword;


        Handler handler = new Handler(){
            public void handleMessage(Message message){
                switch (message.what){
                    case SUCCESS:
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case DEFEATE:
                        Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditText)findViewById(R.id.account);
        passwordEdit = (EditText)findViewById(R.id.password);
        rememberPassword = (CheckBox) findViewById(R.id.remember_pass);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        boolean isRemember = pref.getBoolean("remember_password", false);
        if(isRemember){
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPassword.setChecked(true);
        }
        Log.d("aaaaaaaa","zenmele1");
        login.setOnClickListener(this);
        register.setOnClickListener(this);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String account = accountEdit.getText().toString();
//                String password = passwordEdit.getText().toString();
//                RequestBody requestBody = new FormBody.Builder().add("username",account)
//                        .add("password",password).build();
//                Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer//loginServlet")
//                        .post(requestBody).build();
//                try {
//                    Response response = client.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.d("aaa","aaaaaa");
//                Log.d("aaa",account);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                sendRequestLogin();
                break;
            case R.id.register:
                Log.d("aaaaaaaa","zenmele");
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
     private void sendRequestLogin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(6000, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(6000, TimeUnit.SECONDS)//设置读取超时时间
                            .build();;
                    String account = accountEdit.getText().toString();
                    String password = passwordEdit.getText().toString();
                    RequestBody requestBody = new FormBody.Builder().add("username",account)
                            .add("password",password).build();
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/APPServer/loginServlet")
                            .post(requestBody).build();
                    //10.0.2.2     192.168.11.125
                    Response response = client.newCall(request).execute();
                    String s = response.body().string();
                    Log.d("aaa",s+"2222");
                    Message message = new Message();
                    if(s.equals("1")){
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
                        editor = pref.edit();
                        if(rememberPassword.isChecked()){
                            editor.putBoolean("remember_password",true);
                            editor.putString("account",account);
                            editor.putString("password",password);
                        }else {
                            editor.clear();
                        }
                        editor.apply();
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
