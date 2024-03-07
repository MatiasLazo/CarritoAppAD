package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.teknos.matias.carritoappad.Entitats.Product;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    private AppSingleton appSingleton;
    private Product product;
    private List<Product> productList;
    private char operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.appSingleton = AppSingleton.getInstance();
        productList = appSingleton.getLproducts();

        final long DELAY = 1000; //5000 milisegons = 5 segons

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appSingleton.readUsers(new AppSingleton.FirestoreCallback() {
                    @Override
                    public void onCallback(HashMap<String, JSONObject> hashMap) {
                        Log.d("Read", "done reading users" );
                    }
                });
               
                Intent i = new Intent(SplashScreen.this,LoginScreen.class);
                startActivity(i);
                finish();
            }
        }, DELAY);
    }

}