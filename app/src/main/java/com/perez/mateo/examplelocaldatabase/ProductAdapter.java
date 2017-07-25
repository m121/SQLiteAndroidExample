package com.perez.mateo.examplelocaldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

/**
 * Created by pc on 20/07/2017.
 */

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> arrayList_products = new ArrayList<>(0);
    private ArrayList<String> arrayList_nameproduct = new ArrayList<>(0);
    private ArrayList<String> arrayList_picproduct = new ArrayList<>(0);
    private ArrayList<String> arrayList_priceproduct = new ArrayList<>(0);
    private ArrayList<Integer> arrayList_idproduct = new ArrayList<>(0);

    Product product = new Product();




    public ProductAdapter(Context context, ArrayList<String> arrayList_nameproduct, ArrayList<String> arrayList_picproduct, ArrayList<String> arrayList_priceproduct, ArrayList<Integer> arrayList_idproduct) {
        this.context = context;
        this.arrayList_nameproduct = arrayList_nameproduct;
        this.arrayList_picproduct = arrayList_picproduct;
        this.arrayList_priceproduct = arrayList_priceproduct;
        this.arrayList_idproduct = arrayList_idproduct;


    }

    @Override
    public Product getItem(int position) {
        return arrayList_products.get(position);
    }


    @Override
    public int getCount() {
        return arrayList_nameproduct.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_activitylist, viewGroup, false);
        }

        ConstraintLayout constraintLayout_constraintitem = (ConstraintLayout) view.findViewById(R.id.constraintLayout_item_constraintitem);
        ImageView imageView_picproduct = (ImageView) view.findViewById(R.id.imageView_item_picproduct);
        TextView textView_nameproduct = (TextView) view.findViewById(R.id.textView_item_nameproduct);
        TextView textView_priceproduct = (TextView) view.findViewById(R.id.textView_item_priceproduct);

        constraintLayout_constraintitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .title("Update Product")
                        .input("Name", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                                updaterowdatabase(position,input);

                            }
                        })
                        .content("¿Are you sure you want to Update the product?")
                        .positiveText("Save")
                        .negativeText("Cancel")
                        .show();

            }


        });

        constraintLayout_constraintitem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .title("Remove Product")
                        .content("¿Are you sure you want to remove the product?")
                        .positiveText("Yes")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                deleterowdatabase(position);
                            }
                        })
                        .show();
                return true;
            }
        });


        textView_nameproduct.setText(arrayList_nameproduct.get(position));
        textView_priceproduct.setText(arrayList_priceproduct.get(position));


        imageView_picproduct.setImageBitmap(convertbase64ToImage(position));


        return view;
    }

    private Bitmap convertbase64ToImage(int position) {
        byte[] string_image = Base64.decode(arrayList_picproduct.get(position), Base64.DEFAULT);
        Bitmap bitmap_image = BitmapFactory.decodeByteArray(string_image, 0, string_image.length);

        return bitmap_image;
    }

    private void deleterowdatabase(int position) {
        String querie = "DELETE FROM product_list WHERE _id=" + arrayList_idproduct.get(position);
        SQLiteDatabase db = new SQLiteProductList(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(querie, null);
        cursor.moveToFirst();
        Toast.makeText(context, "Removed, Pull to refresh", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();


    }

    private void updaterowdatabase(int position, CharSequence input) {

        String querie = "SELECT _id,name FROM product_list WHERE _id=" + arrayList_idproduct.get(position);
        SQLiteDatabase db = new SQLiteProductList(context).getReadableDatabase();
        Cursor cursor = db.rawQuery(querie, null);
        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                do {
                    String iditem = cursor.getString(0);
                    ContentValues values = new ContentValues();
                    values.put("name", input.toString());
                    db.update("product_list", values, "_id=" + iditem, null);

                } while (cursor.moveToNext());

                Toast.makeText(context, "Saved!, Pull to refresh", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();

            }
        }


    }


}
