package com.teknos.matias.carritoappad;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickWallet(View view ){
        Intent i = new Intent(MainActivity.this, WalletScreen.class);
        startActivity(i);
    }
    public void onClickSupermarket(View view ){

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Close APP")
                .setMessage("Are you sure you want to close SHOPPINGFY?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Se cancelo la operacion", Toast.LENGTH_SHORT).show();
                    }
                }).show();

    }

    public void onClickShoppingCart(View view ){

        Intent i = new Intent(MainActivity.this, ShoppingCart.class);
        startActivity(i);
    }
    public void onClickDevScreen(View view ){
        Intent i = new Intent(MainActivity.this, DevScreen.class);
        startActivity(i);
    }
}