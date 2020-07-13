package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener {
    private Button lowerHt;
    private Button floorHt;
    private Button clazzHt;
    private Button lightHt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        lowerHt = findViewById(R.id.lower_ht);
        floorHt = findViewById(R.id.floor_ht);
        clazzHt = findViewById(R.id.clazz_ht);
        lightHt = findViewById(R.id.light_ht);

        lowerHt.setOnClickListener(this);
        floorHt.setOnClickListener(this);
        clazzHt.setOnClickListener(this);
        lightHt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lower_ht:
                Intent intent = new Intent(ManagementActivity.this,TowerManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.floor_ht:
                Intent intent1 = new Intent(ManagementActivity.this,FloorManagementActivity.class);
                startActivity(intent1);
                break;
            case R.id.clazz_ht:
                Intent intent2 = new Intent(ManagementActivity.this,ClazzManagementActivity.class);
                startActivity(intent2);
                break;
            case R.id.light_ht:
                Intent intent3 = new Intent(ManagementActivity.this,LightManagementActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
