package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TowerManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private Button towerAdd;
    private Button towerDelete;
    private Button towerUpdate;
    private Button towerOffLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_management);

        towerAdd = findViewById(R.id.tower_add);
        towerDelete = findViewById(R.id.tower_delete);
        towerUpdate = findViewById(R.id.tower_update);
        towerOffLight = findViewById(R.id.tower_off_light);

        towerAdd.setOnClickListener(this);
        towerDelete.setOnClickListener(this);
        towerUpdate.setOnClickListener(this);
        towerOffLight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tower_add:
                Intent intent = new Intent(TowerManagementActivity.this,TowerAddActivity.class);
                startActivity(intent);
                break;
            case R.id.tower_delete:
                Intent intent1 = new Intent(TowerManagementActivity.this,TowerDeleteActivity.class);
                startActivity(intent1);
                break;
            case R.id.tower_update:
                Intent intent2 = new Intent(TowerManagementActivity.this,TowerUpdateActivity.class);
                startActivity(intent2);
                break;
            case R.id.tower_off_light:
                Intent intent3 = new Intent(TowerManagementActivity.this,TowerOffLightActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
