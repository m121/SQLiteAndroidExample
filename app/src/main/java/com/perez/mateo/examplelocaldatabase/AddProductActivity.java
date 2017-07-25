package com.perez.mateo.examplelocaldatabase;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    Product product = new Product();

    private ImageView imageView_pic;
    private EditText editText_name;
    private EditText editText_price;
    private Button button_save;



    public static final int IMAGEREQUESTCODE = 45535;
    Intent galleryIntent;
    Bitmap pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);


        galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        imageView_pic = (ImageView) findViewById(R.id.imageView_addproduct_addpic);
        editText_name = (EditText) findViewById(R.id.editText_addproduct_nameproduct);
        editText_price = (EditText) findViewById(R.id.editText_addproduct_priceproduct);
        button_save = (Button) findViewById(R.id.button_addproduct_saveproduct);

        imageView_pic.setClickable(true);


        button_save.setOnClickListener(this);


        imageView_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectpic();

            }
        });


    }

    @Override
    public void onClick(View view) {
        addproductdatabase();
    }


    private void addproductdatabase() {
        product.setNameproduct(editText_name.getText().toString());
        product.setPriceproduct(editText_price.getText().toString());


        SQLiteDatabase database = new SQLiteProductList(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteProductList.PRODUCT_COLUMN_PIC, product.getPicproduct());
        values.put(SQLiteProductList.PRODUCT_COLUMN_NAME, product.getNameproduct());
        values.put(SQLiteProductList.PRODUCT_COLUMN_PRICE, product.getPriceproduct());
        database.insert(SQLiteProductList.BASE_TABLE_NAME, null, values);

        Toast.makeText(this,"Saved!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(AddProductActivity.this,ListActivity.class);
        startActivity(intent);


    }

    public void selectpic() {

        //select pic from gallery
        startActivityForResult(galleryIntent, IMAGEREQUESTCODE);
    }

    public final void onActivityResult(final int requestCode,
                                       final int resultCode, final Intent i) {
        super.onActivityResult(requestCode, resultCode, i);


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGEREQUESTCODE:
                    manageImageFromUri(i.getData());

            }
        }


    }

    private void manageImageFromUri(Uri imageUri) {
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(), imageUri);

        } catch (Exception e) {
            Log.d("addproduct", "error" + e.toString());
        }

        if (bitmap != null) {
                // Convert image to base64
            pic = bitmap;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] array = stream.toByteArray();
            product.setPicproduct(Base64.encodeToString(array, 0));
            imageView_pic.setImageBitmap(Bitmap.createScaledBitmap(pic, 578, 345, false));
        }
    }
}
