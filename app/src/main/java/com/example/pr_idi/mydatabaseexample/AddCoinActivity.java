package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import static java.lang.Double.*;

public class AddCoinActivity extends AppCompatActivity {

//    private Button btnImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcoin);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnadd:
                EditText et = (EditText) findViewById(R.id.currency);
                String currency = et.getText().toString();

                et = (EditText) findViewById(R.id.value);
                Double value = 0.0;
                try {
                    value = Double.parseDouble(et.getText().toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }

                et = (EditText) findViewById(R.id.year);
                Integer year = 1;
                try {
                    year = Integer.parseInt(et.getText().toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                et = (EditText)findViewById(R.id.country);
                String country = et.getText().toString();
                et = (EditText)findViewById(R.id.description);
                String description = et.getText().toString();
                if (!currency.isEmpty() && value != 0.0) {
                    CoinData coinData = new CoinData(this);
                    coinData.open();
                    coinData.createCoin(currency,value,year,country,description);
                    coinData.close();
                    Toast.makeText(this,"Coin added!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(this,"Empty fields!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btncancel:
                finish();
                break;
            default:
                break;
        }
    }
}
