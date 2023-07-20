package com.madhuurstta.wbvjkmatka;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Wallet extends AppCompatActivity {

    private ImageView back;
    private RelativeLayout deposit;
    private RelativeLayout withdraw;
    private RelativeLayout bidHistory;
    private RelativeLayout winningHistory;
    private RelativeLayout transactionHistory;
    private latobold amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        initViews();
        back.setOnClickListener(v -> onBackPressed());

        amount.setText(getSharedPreferences(constant.prefs,MODE_PRIVATE).getString("wallet","0")+"₹");

        deposit.setOnClickListener(v -> startActivity(new Intent(Wallet.this, deposit_money.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        withdraw.setOnClickListener(v -> startActivity(new Intent(Wallet.this, withdraw.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        bidHistory.setOnClickListener(v -> startActivity(new Intent(Wallet.this, played.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        winningHistory.setOnClickListener(v -> startActivity(new Intent(Wallet.this, ledger.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
        transactionHistory.setOnClickListener(v -> startActivity(new Intent(Wallet.this, transactions.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

    private void initViews() {
        back = findViewById(R.id.back);
        deposit = findViewById(R.id.deposit);
        withdraw = findViewById(R.id.withdraw);
        bidHistory = findViewById(R.id.bid_history);
        winningHistory = findViewById(R.id.winning_history);
        transactionHistory = findViewById(R.id.transaction_history);
        amount = findViewById(R.id.amount);
    }
}