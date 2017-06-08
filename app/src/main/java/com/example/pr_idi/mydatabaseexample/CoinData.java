package com.example.pr_idi.mydatabaseexample;

/**
 * BookData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CoinData {

    // Database fields
    private SQLiteDatabase database;

    // Helper to manipulate table
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Author, must select the appropriate columns
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_CURRENCY,
            MySQLiteHelper.COLUMN_VALUE,
            MySQLiteHelper.COLUMN_YEAR,
            MySQLiteHelper.COLUMN_COUNTRY,
            MySQLiteHelper.COLUMN_DESCRIPTION};

    public CoinData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Coin createCoin(String currency, double value, int year, String country, String description) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + Double.toString(value) + " " + currency);

        values.put(MySQLiteHelper.COLUMN_CURRENCY, currency);
        values.put(MySQLiteHelper.COLUMN_VALUE, value);
        values.put(MySQLiteHelper.COLUMN_COUNTRY, country);
        values.put(MySQLiteHelper.COLUMN_YEAR, year);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_COINS, null,
                values);

        // Main activity calls this procedure to create a new book
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COINS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Coin newCoin = cursorToCoin(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newCoin;
    }
    public void update(Coin coin) {
        long id = coin.getId();

        ContentValues contentValues= new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_CURRENCY,coin.getCurrency());
        contentValues.put(MySQLiteHelper.COLUMN_VALUE,coin.getValue());
        contentValues.put(MySQLiteHelper.COLUMN_YEAR,coin.getYear());
        contentValues.put(MySQLiteHelper.COLUMN_COUNTRY,coin.getCountry());
        contentValues.put(MySQLiteHelper.COLUMN_DESCRIPTION,coin.getDescription());

        //database.update(MySQLiteHelper.TABLE_COINS, contentValues,"_id=?",new String[] {String.valueOf(coin.getId())});
        database.update(MySQLiteHelper.TABLE_COINS, contentValues,MySQLiteHelper.COLUMN_ID + " = " + id,null);
    }

    public void deleteCoin(Coin coin) {
        long id = coin.getId();
        Log.d("Deleting", "Coin deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COINS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
    public void deleteAllCoins(){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COINS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Coin coin = cursorToCoin(cursor);
            long id = coin.getId();
            database.delete(MySQLiteHelper.TABLE_COINS, MySQLiteHelper.COLUMN_ID + " = " + id, null);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public List<Coin> getAllCoins() {
        List<Coin> coins = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COINS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Coin coin = cursorToCoin(cursor);
            coins.add(coin);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return coins;
    }

    private Coin cursorToCoin(Cursor cursor) {
        Coin coin = new Coin();
        coin.setId(cursor.getLong(0));
        coin.setCurrency(cursor.getString(1));
        coin.setValue(cursor.getDouble(2));
        coin.setYear(cursor.getInt(3));
        coin.setCountry(cursor.getString(4));
        coin.setDescription(cursor.getString(5));
        return coin;
    }
}