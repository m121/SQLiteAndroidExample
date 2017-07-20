package com.perez.mateo.examplelocaldatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 19/07/2017.
 */

public class SQLiteProductList extends SQLiteOpenHelper {
    // base de datos para la lista de compra del usuario


    private static final int DATABASE_VERSION = 2;
    public static final String BASE_NAME = "base_products";
    public static final String BASE_TABLE_NAME = "product_list";
    public static final String PRODUCT_COLUM_ID = "_id";
    public static final String PRODUCT_COLUMN_PIC = "pic";
    public static final String PRODUCT_COLUMN_NAME = "name";
    public static final String PRODUCT_COLUMN_PRICE = "price";
    public static final String PRODUCT_COLUMN_IDPRODUCT = "idproduct";



    public SQLiteProductList(Context context) {
        super(context, BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + BASE_TABLE_NAME + " (" +
                PRODUCT_COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_COLUMN_PIC + " TEXT, " +
                PRODUCT_COLUMN_NAME + " TEXT, " +
                PRODUCT_COLUMN_PRICE + " TEXT, " +
                PRODUCT_COLUMN_IDPRODUCT + " TEXT, " +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BASE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
