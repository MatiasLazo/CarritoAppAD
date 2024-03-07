package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;

public class LoginScreen extends AppCompatActivity {

    private AppSingleton appSingleton;
    private User user;
    private TextView edUserName;
    private TextView edPwd;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loadForm();
        this.appSingleton = AppSingleton.getInstance();
        this.user = appSingleton.getUser();
    }
    public void register(View view){
        Intent i = new Intent(LoginScreen.this, RegisterDetail.class);
        startActivity(i);
        finish();
    }

    public void login(View view){
        if (!appSingleton.checkRegister(edUserName.getText().toString())){
            tvError.setVisibility(View.VISIBLE);
        }else {
            Intent i = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void onClickNext(View view){
        Intent i = new Intent(LoginScreen.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void loadForm(){
        this.edUserName = findViewById(R.id.edUserName);
        this.edPwd = findViewById(R.id.edPwd);
        this.tvError = findViewById(R.id.tvError);
    }

}