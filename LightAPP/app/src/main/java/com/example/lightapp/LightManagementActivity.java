package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LightManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private Button lightAdd;
    private Button lightDelete;
    private Button lightUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_management);

        lightAdd = findViewById(R.id.light_add);
        lightDelete = findViewById(R.id.light_delete);
        lightUpdate = findViewById(R.id.light_update);


        lightAdd.setOnClickListener(this);
        lightDelete.setOnClickListener(this);
        lightUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.light_add:
                Intent intent = new Intent(LightManagementActivity.this,LightAddActivity.class);
                startActivity(intent);
                break;
            case R.id.light_delete:
                Intent intent1 = new Intent(LightManagementActivity.this,LightDeleteActivity.class);
                startActivity(intent1);
                break;
            case R.id.light_update:
                Intent intent2 = new Intent(LightManagementActivity.this,LightUpdateActivity.class);
                startActivity(intent2);
                break;

        }
    }
}
