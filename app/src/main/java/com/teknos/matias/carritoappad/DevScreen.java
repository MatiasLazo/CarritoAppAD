package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class DevScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_screen);

    }
    public void backToMain(View view){
        Intent i = new Intent(DevScreen.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}