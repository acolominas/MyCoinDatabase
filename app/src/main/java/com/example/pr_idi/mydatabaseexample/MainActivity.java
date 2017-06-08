package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
    private CoinData coinData;
    private ArrayAdapter<Coin> adapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView)findViewById(android.R.id.list);
        adapter = (ArrayAdapter<Coin>) getListAdapter();
        coinData = new CoinData(this);
        coinData.open();
        if (coinData.getAllCoins().isEmpty()) {
            coinData.createCoin("Euro", 1.0, 2001, "Europa", "Moneda Europea");
            coinData.createCoin("Dolar", 1.15, 1875, "USA", "Moneda EEUU");
            coinData.createCoin("ARS", 18, 1992, "Argentina", "Moneda Argentina");
            coinData.createCoin("Yuan", 7.5, 1949, "Chine", "Moneda China");
        }
        coinData.close();
    }

    // Basic method to add pseudo-random list of books so that
    // you have an example of insertion and deletion

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")

        Coin coin;
        switch (view.getId()) {
            case R.id.add:
                startActivity(new Intent(this,AddCoinActivity.class));
                updateCoins();
                break;
            default:
                break;
        }
        //adapter.notifyDataSetChanged();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onResume() {

        updateCoins();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Coin coin = (Coin)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(),Coin_info.class);
                intent.putExtra("id",coin.getId());
                intent.putExtra("currency",coin.getCurrency());
                intent.putExtra("value",coin.getValue());
                intent.putExtra("year",coin.getYear());
                intent.putExtra("country",coin.getCountry());
                intent.putExtra("description",coin.getDescription());
                startActivity(intent);
            }
        });

        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void updateCoins(){
        coinData.open();
        List<Coin> values = coinData.getAllCoins();
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, values);

        //setListAdapter(adapter);
        listView.setAdapter(adapter);//setListAdapter(adapter);
        coinData.close();
    }
}