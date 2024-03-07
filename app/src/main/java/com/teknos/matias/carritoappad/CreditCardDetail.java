package com.teknos.matias.carritoappad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teknos.matias.carritoappad.Entitats.CreditCard;
import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;


import java.util.List;

public class CreditCardDetail extends AppCompatActivity {

    private EditText edTitularName;
    private EditText edBalanceWanted;
    private Spinner spServices;
    private ImageView ivCreditCard;
    private EditText  edValid;
    private EditText edNumber;
    private EditText edCVV;
    private CreditCard currentCreditCard;
    private AppSingleton appSingleton;
    private List<CreditCard> creditCards;
    private String[] services;
    private User user;
    private Button btCCRemove;
    private Button btBalance;
    private Button btAddCC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card_detail);
        loadForm();
        this.appSingleton = AppSingleton.getInstance();
        this.currentCreditCard =  appSingleton.getCurrentCreditCard();
        this.creditCards = appSingleton.getLcreditCards();
        this.user = appSingleton.getUser();

        if (!currentCreditCard.getName().equals("N/A")){
            setViews();
            changeViews(false);
            btBalance.setVisibility(View.VISIBLE);
            btCCRemove.setVisibility(View.VISIBLE);
            btAddCC.setVisibility(View.INVISIBLE);
            this.edBalanceWanted.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CreditCardDetail.this, WalletScreen.class);
        startActivity(i);
        finish();
    }

    private void setViews(){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground);

        Glide.with(this).load(currentCreditCard.getURL()).apply(options).into((ivCreditCard));
        this.edTitularName.setText(currentCreditCard.getName());
        this.edValid.setText(currentCreditCard.getValidThru());
        this.edNumber.setText(String.valueOf(currentCreditCard.getCardNumber()));
        this.edCVV.setText(String.valueOf(currentCreditCard.getCvv()));
        int selection = currentCreditCard.getService().equals("mastercard") ? 0 : 1;

        this.spServices.post(new Runnable() {
            @Override
            public void run() {
                spServices.setSelection(selection);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onClickAddCreditCard(View view){
        setAttr();
        appSingleton.addCreditCard((currentCreditCard));
        Intent i = new Intent(CreditCardDetail.this,WalletScreen.class);
        startActivity(i);
        finish();
    }

    public void onClickAddBalance(View view){
        if (!edBalanceWanted.getText().equals("")){
        int balance = Integer.parseInt(edBalanceWanted.getText().toString());
        user.setBalance(balance+user.getBalance());
        appSingleton.updateUser("balance",String.valueOf(balance));
        }
        Intent i = new Intent(CreditCardDetail.this, WalletScreen.class);
        startActivity(i);
        finish();

    }
    public void onClickRemoveCard(View view){
        appSingleton.deleteCreditCard();
        Intent i = new Intent(CreditCardDetail.this, WalletScreen.class);
        startActivity(i);
        finish();
    }

    private void changeViews(boolean enable){
        this.edTitularName.setEnabled(enable);
        this.edValid.setEnabled(enable);
        this.edNumber.setEnabled(enable);
        this.edCVV.setEnabled(enable);
        this.spServices.setEnabled(enable);

    }
    private void setAttr(){
        int cvv = Integer.parseInt(edCVV.getText().toString());
        currentCreditCard.setService(spServices.getSelectedItem().toString());
        currentCreditCard.setCardNumber(Integer.parseInt(edNumber.getText().toString()));
        currentCreditCard.setName(edTitularName.getText().toString());
        currentCreditCard.setCvv(cvv);
        currentCreditCard.setValidThru(edValid.getText().toString());
        currentCreditCard.setURL(currentCreditCard.getService());

    }


    public void generateSpinner() {
        this.services = new String[]{"mastercard", "visa"};
        this.spServices = findViewById(R.id.spServices);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,services);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spServices.setAdapter(aa);
    }


    private void loadForm(){
        this.edTitularName = findViewById(R.id.edTitularName);
        this.edBalanceWanted = findViewById(R.id.edBalanceWanted);
        this.edValid = findViewById(R.id.edValid);
        this.edNumber = findViewById(R.id.edNumber);
        this.edCVV = findViewById(R.id.edCVV);
        this.ivCreditCard = findViewById(R.id.ivCreditCard);
        generateSpinner();
        this.btBalance = findViewById(R.id.btBalance);
        this.btCCRemove = findViewById(R.id.btCCRemove);
        this.btAddCC = findViewById(R.id.btAddCC);

    }
}