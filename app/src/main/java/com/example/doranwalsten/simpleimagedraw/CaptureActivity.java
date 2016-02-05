package com.example.doranwalsten.simpleimagedraw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

public class CaptureActivity extends AppCompatActivity {


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        /*
        String[] test = {"Parameter 1", "Parameter 2"};
        ArrayList<Double[]> test_list = new ArrayList<Double[]>();
        Double[] test_bounds = {0.0, 10.0};
        test_list.add(test_bounds);
        test_bounds[0] = 10.0;
        test_bounds[1] = 20.0;
        test_list.add(test_bounds);
        SliderList menu = new SliderList(CaptureActivity.this,test,test_list);
        //myTable = (ListView) findViewById(R.id.testTable);
        //myTable.setAdapter(menu);
        */


    }

    public void captureButtonClicked(View view) {
        dispatchTakePictureIntent();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) throws NullPointerException {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                //FileOutputStream out = new FileOutputStream(file);
                Intent intent = new Intent(CaptureActivity.this, EditImage.class);
                intent.putExtra("Background",imageBitmap);
                startActivity(intent);
                //imageBitmap.compress(Bitmap.CompressFormat.PNG,100,out);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
