package com.perez.mateo.examplelocaldatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {


    ListView listview_products;
    Button   button_addproduct;
    SwipeRefreshLayout swipeRefreshLayout_list;
    ProductAdapter productAdapter;


    private ArrayList<String> arraylist_nameproduct;
    private ArrayList<String> arraylist_priceproduct;
    private ArrayList<String> arrayList_picsproduct;
    private ArrayList<Integer> arrayList_idproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listview_products = (ListView) findViewById(R.id.listView_activitylist_listproducts);
        button_addproduct = (Button) findViewById(R.id.button_activitylist_addproductsbtn);
        swipeRefreshLayout_list = (SwipeRefreshLayout) findViewById(R.id.SwipeRefresh_activitylist_refreshlist);

        swipeRefreshLayout_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readfromDatabase();
                swipeRefreshLayout_list.setRefreshing(false);

            }
        });

        button_addproduct.setOnClickListener(this);

        readfromDatabase();



    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,AddProductActivity.class);
        startActivity(intent);

    }

    public void readfromDatabase() {
        String querie = "SELECT * FROM product_list";

        arraylist_nameproduct = new ArrayList<>(0);
        arraylist_priceproduct = new ArrayList<>(0);
        arrayList_picsproduct = new ArrayList<>(0);
        arrayList_idproduct = new ArrayList<>(0);


        SQLiteDatabase db = new SQLiteProductList(this).getReadableDatabase();
        Cursor cursor = db.rawQuery(querie, null);
        if (cursor.moveToFirst()) {
            do {

                arraylist_nameproduct.add(cursor.getString(2));
                arraylist_priceproduct.add(cursor.getString(3));
                arrayList_picsproduct.add(cursor.getString(1));
                arrayList_idproduct.add(cursor.getInt(0));



            } while (cursor.moveToNext());

            productAdapter = new ProductAdapter(this,arraylist_nameproduct,arrayList_picsproduct,arraylist_priceproduct,arrayList_idproduct);
            listview_products.setAdapter(productAdapter);
        }
        else
        {
            Toast.makeText(this,"No list!, Add a product please",Toast.LENGTH_LONG).show();
        }






    }
}
