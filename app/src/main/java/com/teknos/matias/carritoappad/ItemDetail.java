package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teknos.matias.carritoappad.Entitats.Product;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;

import org.json.JSONObject;

import java.util.HashMap;


public class ItemDetail extends AppCompatActivity {

    private Product currentProduct;
    private AppSingleton appSingleton;
    private boolean fromQR;
    private ImageView ivDTProductImage;
    private TextView tvDTName;
    private TextView tvDTBrand;
    private TextView tvDTenergeticValue;
    private TextView tvDTexpirationDate;
    private TextView tvDTunits;
    private TextView tvDTmeasures;
    private TextView tvDTPrice;
    private TextView tvDTQuantity;
    private int quantity = 0;
    private Button btAdd;
    private Button btRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        this.appSingleton = AppSingleton.getInstance();
        this.currentProduct = appSingleton.getCurrentProduct();
        this.fromQR = appSingleton.isFromQR();
        loadForm();
        setViews();
        if (fromQR){
            disableButton(btRemove);
            enableButton(btAdd);
        }
        else {
            disableButton(btAdd);
            enableButton(btRemove);
        }
    }

    private void loadForm(){
       this.tvDTName= findViewById(R.id.tvDTName);
       this.tvDTBrand= findViewById(R.id.tvDTBrand);
       this.tvDTenergeticValue= findViewById(R.id.tvDTenergeticValue);
       this.tvDTexpirationDate= findViewById(R.id.tvDTexpirationDate);
       this.tvDTunits= findViewById(R.id.tvDTunits);
       this.tvDTmeasures= findViewById(R.id.tvDTmeasures);
       this.tvDTPrice= findViewById(R.id.tvDTPrice);
       this.tvDTQuantity= findViewById(R.id.tvDTQuantity);
       this.btAdd= findViewById(R.id.btAdd);
       this.btRemove= findViewById(R.id.btRemove);
       this.ivDTProductImage = findViewById(R.id.ivDTProductImage);
    }
    private void setViews(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);

        Glide.with(this).load(currentProduct.getURL()).apply(options).into((ivDTProductImage));

        String measures = currentProduct.getMeasures()+"g";
        String calories = currentProduct.getEnergeticValue()+"kcal";

        this.tvDTName.setText(currentProduct.getName());
        this.tvDTBrand.setText(currentProduct.getBrand());
        this.tvDTenergeticValue.setText(measures);
        this.tvDTexpirationDate.setText(currentProduct.getExpirationDate());
        this.tvDTunits.setText(String.valueOf(currentProduct.getUnits()));
        this.tvDTmeasures.setText(calories);
        this.tvDTPrice.setText(String.valueOf(currentProduct.getPrice()));
        this.tvDTQuantity.setText(String.valueOf(currentProduct.getQuantity()));

    }
    private void disableButton(Button button){
        button.setVisibility(View.INVISIBLE);
    }
    private void enableButton(Button button){
        button.setVisibility(View.VISIBLE);
    }


    public void addProduct(View view){
        appSingleton.addProduct2(currentProduct);
        Intent i = new Intent(ItemDetail.this,ShoppingCart.class);
        startActivity(i);
        finish();
    }
    public void removeProduct(View view){
        appSingleton.deleteProduct();
        Intent i = new Intent(ItemDetail.this, ShoppingCart.class);
        startActivity(i);
        finish();


    }
    public void changeQuantity(View view){
        String tag = view.getTag().toString();
        if (tag.equals("-")){
            decreaseQuantity();
        }
        else {
            increaseQuantity();
        }
    }

    private void decreaseQuantity(){
        if (quantity != 0){
            quantity--;
            tvDTQuantity.setText(String.valueOf(quantity));
        }
    }

    private void increaseQuantity(){
        quantity++;
        tvDTQuantity.setText(String.valueOf(quantity));
    }

}