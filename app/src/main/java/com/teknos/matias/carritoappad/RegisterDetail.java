package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;

public class RegisterDetail extends AppCompatActivity {

    private EditText edUserName;
    private EditText edName;
    private EditText edLastName;
    private EditText edPassword;
    private TextView tvErrorRG;
    private AppSingleton appSingleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_detail);
        loadForm();
        this.appSingleton = AppSingleton.getInstance();
    }

    public void registerForm(View view){
        if (appSingleton.checkRegister(edUserName.getText().toString())){
            tvErrorRG.setVisibility(View.VISIBLE);
        }
        else {
            User user = new User();
            user.setUsername(edUserName.getText().toString());
            user.setName(edName.getText().toString());
            user.setLastname(edLastName.getText().toString());
            user.setPassword(edPassword.getText().toString());
            appSingleton.setUser(user);
            appSingleton.addUser2();

            Intent i = new Intent(RegisterDetail.this, LoginScreen.class);
            startActivity(i);
            finish();
        }
    }

    public void loadForm(){
        this.edUserName = findViewById(R.id.edRGUserName);
        this.edName = findViewById(R.id.edRGName);
        this.edLastName = findViewById(R.id.edLastName);
        this.edPassword = findViewById(R.id.edPassword);
        this.tvErrorRG = findViewById(R.id.tvErrorRG);
    }

}