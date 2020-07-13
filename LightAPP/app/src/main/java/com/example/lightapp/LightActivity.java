package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightapp.data.Light;
import com.example.lightapp.service.AutoUpdateService;
import com.example.lightapp.util.HttpUtil;
import com.example.lightapp.util.Utility;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LightActivity extends AppCompatActivity implements View.OnClickListener{
    private int clazzId;
    private TextView clazz_title;
    private Button on_jt;
    private Button off_jt;
    private Button on_qp;
    private Button off_qp;
    private Button on_zp;
    private Button off_zp;
    private Button on_hp;
    private Button off_hp;
    private ImageView light_jt;
    private ImageView light_qpleft;
    private ImageView light_qpright;
    private ImageView light_zpleft;
    private ImageView light_zpright;
    private ImageView light_hpleft;
    private ImageView light_hpright;
    private ProgressDialog progressDialog;
    Timer timer = new  Timer();   //定义全局变量
    long delay = 0;
    long intevalPeriod = 2* 1000;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }};

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 要做的事情
            clazzId = getIntent().getIntExtra("class_id",0);
            requestLight(clazzId);
            //Toast.makeText(VolleyDemoActivity.this,"ceshi",Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        clazz_title = (TextView) findViewById(R.id.clazz_title);
        on_jt = (Button) findViewById(R.id.on_jt);
        off_jt = (Button) findViewById(R.id.off_jt);
        on_qp = (Button) findViewById(R.id.on_qp);
        off_qp = (Button) findViewById(R.id.off_qp);
        on_zp = (Button) findViewById(R.id.on_zp);
        off_zp = (Button) findViewById(R.id.off_zp);
        on_hp = (Button) findViewById(R.id.on_hp);
        off_hp = (Button) findViewById(R.id.off_hp);
        light_jt = (ImageView) findViewById(R.id.light_jt);
        light_qpleft = (ImageView) findViewById(R.id.light_qpleft);
        light_qpright = (ImageView) findViewById(R.id.light_qpright);
        light_zpleft = (ImageView) findViewById(R.id.light_zpleft);
        light_zpright = (ImageView) findViewById(R.id.light_zpright);
        light_hpleft = (ImageView) findViewById(R.id.light_hpleft);
        light_hpright = (ImageView) findViewById(R.id.light_hpright);
        on_jt.setOnClickListener(this);
        off_jt.setOnClickListener(this);
        on_qp.setOnClickListener(this);
        off_qp.setOnClickListener(this);
        on_zp.setOnClickListener(this);
        off_zp.setOnClickListener(this);
        on_hp.setOnClickListener(this);
        off_hp.setOnClickListener(this);
        //int clazzId = getIntent().getIntExtra("class_id",0);
       // requestLight(clazzId);
        timer.scheduleAtFixedRate(task, delay,intevalPeriod);
//        Intent intent = new Intent(this, AutoUpdateService.class);
//        intent.putExtra("class_id",clazzId);
//        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.on_jt:
                onjt("jtLight");
                break;
            case R.id.off_jt:
                offjt("jtLight");
                break;
            case R.id.on_qp:
                onjt("qpLight");
                break;
            case R.id.off_qp:
                offjt("qpLight");
                break;
            case R.id.on_zp:
                onjt("zpLight");
                break;
            case R.id.off_zp:
                offjt("zpLight");
                break;
            case R.id.on_hp:
                onjt("hpLight");
                break;
            case R.id.off_hp:
                offjt("hpLight");
                break;
        }
    }

    /**
     * 开灯
     * @param lname
     */
    public void onjt(String lname){
        String lightUrl = "http://10.0.2.2:8080/APPServer/onLightServlet?cid="+clazzId+"&lname="+lname;
        HttpUtil.sendOkHttpRequest(lightUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LightActivity.this,"获取灯具信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    /**
     * 关灯
     * @param lname
     */
    public void offjt(String lname){
        String lightUrl = "http://10.0.2.2:8080/APPServer/offLightServlet?cid="+clazzId+"&lname="+lname;
        HttpUtil.sendOkHttpRequest(lightUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LightActivity.this,"获取灯具信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }

    public void requestLight(int clazzId){
        String lightUrl = "http://10.0.2.2:8080/APPServer/selectLightServlet?cid="+clazzId;
        HttpUtil.sendOkHttpRequest(lightUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LightActivity.this,"获取灯具信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<Light> lightList = Utility.handleLightResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (lightList != null){
                            ShowWeatherInfo(lightList);
                        } else {
                            Toast.makeText(LightActivity.this,"获取灯具信息失败",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }

    /**
     * 显示灯具信息
     * @param lightList
     */
    private void ShowWeatherInfo(List<Light> lightList) {
        for (Light light : lightList) {
            if (light.getLname().equals("jtLight")) {
                if (light.getState() == 1) {
                    light_jt.setImageResource(R.mipmap.light_on);
                } else {
                    light_jt.setImageResource(R.mipmap.light_off);
                }
            } else if (light.getLname().equals("qpLight")) {
                if (light.getState() == 1) {
                    light_qpleft.setImageResource(R.mipmap.light_on);
                    light_qpright.setImageResource(R.mipmap.light_on);
                } else {
                    light_qpleft.setImageResource(R.mipmap.light_off);
                    light_qpright.setImageResource(R.mipmap.light_off);
                }
            } else if (light.getLname().equals("zpLight")) {
                if (light.getState() == 1) {
                    light_zpleft.setImageResource(R.mipmap.light_on);
                    light_zpright.setImageResource(R.mipmap.light_on);
                } else {
                    light_zpleft.setImageResource(R.mipmap.light_off);
                    light_zpright.setImageResource(R.mipmap.light_off);
                }
            } else if (light.getLname().equals("hpLight")) {
                if (light.getState() == 1) {
                    light_hpleft.setImageResource(R.mipmap.light_on);
                    light_hpright.setImageResource(R.mipmap.light_on);
                    //light_hpright.setBackgroundDrawable(getResources().getDrawable(R.mipmap.light_on));
                } else {
                    light_hpleft.setImageResource(R.mipmap.light_off);
                    light_hpright.setImageResource(R.mipmap.light_off);
                    //light_hpright.setBackgroundDrawable(getResources().getDrawable(R.mipmap.light_off));
                }
            }
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog(){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LightActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //当Activity停止运行后，取消Activity的所有网络请求
        timer.cancel();
//        MyApplication.getRequestQueue().cancelAll(VolleyRequestDemo.VOLLEY_TAG);
//        Log.i("### onStop", "cancel all:tag=" + VolleyRequestDemo.VOLLEY_TAG);
    }
}


