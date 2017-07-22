package com.perez.mateo.examplelocaldatabase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


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


    public ProductAdapter(Context context, ArrayList<String> arrayList_nameproduct, ArrayList<String> arrayList_picproduct, ArrayList<String> arrayList_priceproduct) {
        this.context = context;
        this.arrayList_nameproduct = arrayList_nameproduct;
        this.arrayList_picproduct = arrayList_picproduct;
        this.arrayList_priceproduct = arrayList_priceproduct;


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
    public int getViewTypeCount() {
        return arrayList_nameproduct.size();
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

        ImageView imageView_picproduct = (ImageView) view.findViewById(R.id.imageView_item_picproduct);
        TextView textView_nameproduct = (TextView) view.findViewById(R.id.textView_item_nameproduct);
        TextView textView_priceproduct = (TextView) view.findViewById(R.id.textView_item_priceproduct);

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


}
