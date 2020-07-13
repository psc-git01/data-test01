package com.example.lightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClazzManagementActivity extends AppCompatActivity implements View.OnClickListener {

    private Button clazzAdd;
    private Button clazzDelete;
    private Button clazzUpdate;
    private Button clazzSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clazz_management);

        clazzAdd = findViewById(R.id.clazz_add);
        clazzDelete = findViewById(R.id.clazz_delete);
        clazzUpdate = findViewById(R.id.clazz_update);
        clazzSelect = findViewById(R.id.clazz_select);

        clazzAdd.setOnClickListener(this);
        clazzDelete.setOnClickListener(this);
        clazzUpdate.setOnClickListener(this);
        clazzSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clazz_add:
                Intent intent = new Intent(ClazzManagementActivity.this,ClazzAddActivity.class);
                startActivity(intent);
                break;
            case R.id.clazz_delete:
                Intent intent1 = new Intent(ClazzManagementActivity.this,ClazzDeleteActivity.class);
                startActivity(intent1);
                break;
            case R.id.clazz_update:
                Intent intent2 = new Intent(ClazzManagementActivity.this,ClazzUpdateActivity.class);
                startActivity(intent2);
                break;
            case R.id.clazz_select:
                Intent intent3 = new Intent(ClazzManagementActivity.this,ClazzOffLightActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
