package com.perez.mateo.examplelocaldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {


    ListView listview_products;
    Button   button_addproduct;


    private ArrayList<String> arraylist_nameproduct;
    private ArrayList<String> arraylist_priceproduct;
    private ArrayList<String> arrayList_picsproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listview_products = (ListView) findViewById(R.id.listView_activitylist_listproducts);
        button_addproduct = (Button) findViewById(R.id.button_activitylist_addproductsbtn);

        button_addproduct.setOnClickListener(this);

        readfromDatabase();



    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,AddProductActivity.class);
        startActivity(intent);

    }

    private void readfromDatabase() {
        String querie = "SELECT * FROM product_list";

        arraylist_nameproduct = new ArrayList<>(0);
        arraylist_priceproduct = new ArrayList<>(0);
        arrayList_picsproduct = new ArrayList<>(0);


        SQLiteDatabase db = new SQLiteProductList(this).getReadableDatabase();
        Cursor cursor = db.rawQuery(querie, null);
        if (cursor.moveToFirst()) {
            do {

                arraylist_nameproduct.add(cursor.getString(2));
                arraylist_priceproduct.add(cursor.getString(3));
                arrayList_picsproduct.add(cursor.getString(1));



            } while (cursor.moveToNext());

        }

        ProductAdapter productAdapter = new ProductAdapter(this,arraylist_nameproduct,arrayList_picsproduct,arraylist_priceproduct);
        listview_products.setAdapter(productAdapter);

    }
}
