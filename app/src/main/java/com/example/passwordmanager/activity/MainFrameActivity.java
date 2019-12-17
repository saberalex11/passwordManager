package com.example.passwordmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.R;

public class MainFrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_frame);
    }

    public void add(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void query(View view){
        Intent intent = new Intent(this, QueryListActivity.class);
        startActivity(intent);
    }
}
