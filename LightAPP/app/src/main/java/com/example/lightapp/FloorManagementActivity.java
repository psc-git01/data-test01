package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FloorManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private Button floorAdd;
    private Button floorDelete;
    private Button floorUpdate;
    private Button floorSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_management);

        floorAdd = findViewById(R.id.floor_add);
        floorDelete = findViewById(R.id.floor_delete);
        floorUpdate = findViewById(R.id.floor_update);
        floorSelect = findViewById(R.id.floor_select);

        floorAdd.setOnClickListener(this);
        floorDelete.setOnClickListener(this);
        floorUpdate.setOnClickListener(this);
        floorSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floor_add:
                Intent intent = new Intent(FloorManagementActivity.this,FloorAddActivity.class);
                startActivity(intent);
                break;
            case R.id.floor_delete:
                Intent intent1 = new Intent(FloorManagementActivity.this,FloorDeleteActivity.class);
                startActivity(intent1);
                break;
            case R.id.floor_update:
                Intent intent2 = new Intent(FloorManagementActivity.this,FloorUpdateActivity.class);
                startActivity(intent2);
                break;
            case R.id.floor_select:
                Intent intent3 = new Intent(FloorManagementActivity.this,FloorOffLightActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
