package com.teknos.matias.carritoappad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.teknos.matias.carritoappad.Entitats.Product;
import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;


import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ShoppingCart extends AppCompatActivity implements ProductsAdapter.OnSelectedProduct,ZXingScannerView.ResultHandler{

    private Product product;
    private List<Product> productList;
    private char operation;
    private User user;
    private AppSingleton appSingleton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private boolean cameraOn;
    private ConstraintLayout contentFrame;
    private Button btPaycheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        appSingleton = AppSingleton.getInstance();
        productList = appSingleton.getLproducts();
        operation = appSingleton.getOperation();
        this.user =appSingleton.getUser();
        recyclerView = findViewById(R.id.rv_items_list);
        if (user.getProducts()!=null){
            appSingleton.readUserProducts();

        }
        LinearLayoutManager ll_manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(ll_manager);

        mAdapter = new ProductsAdapter(productList, ShoppingCart.this);
        recyclerView.setAdapter(mAdapter);
        this.contentFrame = findViewById(R.id.contentFrame);
        this.mScannerView = new ZXingScannerView(this);
        this.cameraOn = false;
        Button btPaycheck = findViewById(R.id.btPaycheck);
        if (user.getProducts()!=null){
            btPaycheck.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id== R.id.save){
            captureQR();
        }

        return(super.onOptionsItemSelected(item));

    }

    public void paycheck(View view){
        new AlertDialog.Builder(this)
                .setTitle("Total: ")
                .setMessage("do you want to pay now?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        appSingleton.deleteAllProducts();
                        mAdapter.notifyItemRangeRemoved(0,productList.size());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ShoppingCart.this, "Se cancelo la operacion", Toast.LENGTH_SHORT).show();
                    }
                }).show();


    }

    public void scanQR (View view){
        captureQR();
    }

    public void startCamera() {
        Log.i("AD_C11", "startCamera");
        contentFrame.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        cameraOn = true;
    }
    public void stopCamera() {
        Log.i("AD_C11", "stopCamera");
        mScannerView.stopCamera();
        cameraOn = false;
    }

    public void captureQR(){
        Log.i("AD_C11", "captureQR");
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){
            this.startCamera();
        }
        else{
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }


    @Override
    public void handleResult(Result rawResult) {
        Log.i("AD_C11", "handleResult");
        if (rawResult!=null) {
            appSingleton.setFromQR(true);
            Log.i("AD_C11" , rawResult.getText());
            Gson gson = new Gson();
            Product product = gson.fromJson(rawResult.getText(),Product.class);
            appSingleton.setCurrentProduct(product);
            Intent i = new Intent(ShoppingCart.this, ItemDetail.class);
            startActivity(i);
            finish();
        }
        stopCamera();

    }
    @Override
    public void onBackPressed() {
        Log.i("AD_C11", "onBackPressed");
        if (cameraOn) {
            stopCamera();
        } else {
            // Estat per defecte
            super.onBackPressed();
        }
    }
    @Override
    protected void onPause() {
        Log.i("AD_C11", "onPause");
        if (cameraOn)
            stopCamera();
        super.onPause();
    }

    @Override
    public void OnSelectedDetail(Product product) {
        AppSingleton.getInstance().setOperation('R');
        Intent i = new Intent(ShoppingCart.this,ItemDetail.class);
        appSingleton.setCurrentProduct(product);
        startActivity(i);
    }
}