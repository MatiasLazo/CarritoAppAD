package com.teknos.matias.carritoappad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.teknos.matias.carritoappad.Entitats.CreditCard;
import com.teknos.matias.carritoappad.Entitats.User;
import com.teknos.matias.carritoappad.Singletons.AppSingleton;

import java.util.List;

public class WalletScreen extends AppCompatActivity implements CardsAdapter.onSelectedCard {
    private List<CreditCard> creditCards;
    private AppSingleton appSingleton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private CreditCard currentCreditCard;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_screen);
        appSingleton = AppSingleton.getInstance();
        creditCards = appSingleton.getLcreditCards();
        user = appSingleton.getUser();

        appSingleton.setCtx(this);
        recyclerView = findViewById(R.id.rv_card_list);

        appSingleton.readUserCreditCards();

        LinearLayoutManager ll_manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(ll_manager);
        mAdapter = new CardsAdapter(creditCards, WalletScreen.this);
        recyclerView.setAdapter(mAdapter);

        TextView tvCurrentBalance = findViewById(R.id.tvCurrentBalance);
        tvCurrentBalance.setText(String.valueOf(user.getBalance()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WalletScreen.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void OnSelectedDetail(CreditCard creditCard){
        appSingleton.setCurrentCreditCard(creditCard);
        Intent i = new Intent(WalletScreen.this, CreditCardDetail.class);
        startActivity(i);
    }

    public void onAddCreditCard(View view){
        appSingleton.setCurrentCreditCard(new CreditCard());
        Intent i = new Intent(WalletScreen.this, CreditCardDetail.class);
        startActivity(i);
    }

}