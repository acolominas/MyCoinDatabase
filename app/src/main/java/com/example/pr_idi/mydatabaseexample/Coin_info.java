package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Coin_info extends AppCompatActivity {

    private EditText year;
    private EditText description;
    private Button guardar;
    private Coin coin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);

        Bundle datos = getIntent().getExtras();
        // Instacias de los botones
        TextView currency = (TextView)findViewById(R.id.currency);
        TextView value = (TextView)findViewById(R.id.value);
        TextView country = (TextView)findViewById(R.id.country);

        year = (EditText)findViewById(R.id.year);
        description = (EditText)findViewById(R.id.description);
        guardar = (Button)findViewById(R.id.save);

        currency.setText(datos.getString("currency"));
        value.setText(String.valueOf(datos.getDouble("value")));
        country.setText(datos.getString("country"));
        year.setText(String.valueOf(datos.getInt("year")));
        description.setText(datos.getString("description"));

        coin = new Coin();
        coin.setId(datos.getLong("id"));
        coin.setValue(datos.getDouble("value"));
        coin.setCurrency(datos.getString("currency"));
        coin.setCountry(datos.getString("country"));
        coin.setYear(datos.getInt("year"));
        coin.setDescription(datos.getString("description"));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void modDatabase(String action)
    {
        CoinData coinData = new CoinData(this);
        coinData.open();
        switch (action){
            case "delete":
                coinData.deleteCoin(coin);
                break;
            case "update":
                year = (EditText)findViewById(R.id.year);
                description = (EditText)findViewById(R.id.description);
                coin.setDescription(description.getText().toString());
                coin.setYear(Integer.parseInt(year.getText().toString()));
                coinData.update(coin);
                break;
            default:
                break;
        }
        coinData.close();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modDesc:
                description.setEnabled(true);
                guardar.setEnabled(true);
                break;
            case R.id.modYear:
                year.setEnabled(true);
                guardar.setEnabled(true);
                break;
            case R.id.delete:
                modDatabase("delete");
                finish();
                break;
            case R.id.save:
                modDatabase("update");
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }
}
