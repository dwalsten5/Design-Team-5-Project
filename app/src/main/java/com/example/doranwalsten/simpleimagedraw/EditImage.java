package com.example.doranwalsten.simpleimagedraw;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditImage extends AppCompatActivity {

    File myDir=new File(Environment.getExternalStorageDirectory().getPath());
    String pic_name = "patient_face.png";
    File file = new File(myDir,pic_name);
    FileOutputStream out = null;

    ImageView mImageView;
    ListView shapeList;
    Button finished;
    ArrayList<View> annotations;
    Boolean adding = false;
    String[] names = {
            //"Circle",
            //"Square",
            //"Triangle",
            "Rhomboid"
    };
    Integer[] imageId = {
            //R.drawable.image1,
            //R.drawable.image2,
            //R.drawable.image3,
            R.drawable.image3
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        mImageView = (ImageView) findViewById(R.id.ImageView);
        mImageView.setImageBitmap((Bitmap) this.getIntent().getExtras().get("Background"));

        //Create Shape List
        CustomList menu = new CustomList(EditImage.this,names,imageId);
        shapeList = (ListView) findViewById(R.id.ShapeList);
        shapeList.setAdapter(menu);
        shapeList.setVisibility(View.GONE);

        //Set Action Listeneres for List
        shapeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                adding = true;
                //Create new Image View that can be moved around
                /*
                final RelativeLayout home = (RelativeLayout) findViewById(R.id.EditImageLayout);
                final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                if (position < 3) {
                    ImageView new_edit = new ImageView(parent.getContext());
                    new_edit.setImageResource(imageId[position]);
                    new_edit.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            ClipData clipData = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                            v.startDrag(clipData, shadowBuilder, v, 0);
                            v.setVisibility(View.GONE);
                            return true;
                        }
                    });
                    new_edit.layout(50, 50, 50, 50);

                    home.addView(new_edit, lp);
                    annotations.add(new_edit);
                }
                 */
                Intent intent = new Intent(EditImage.this, RhomboidActivity.class);
                intent.putExtra("Background", ((BitmapDrawable) mImageView.getDrawable()).getBitmap());
                shapeList.setVisibility(View.GONE);
                startActivity(intent);
            }
        });


        //Set Action listeners
        mImageView.setOnTouchListener(new OnSwipeTouchListener(null) {

            public void onSwipeLeft() {
                //Make menu appear
                if (!adding) { //Only want this possible when we are not adding a shape
                    shapeList.setVisibility(View.VISIBLE);
                }
            }

            public void onSwipeRight() {
                //Make menu disappear
                if (!adding) {
                    shapeList.setVisibility(View.GONE);
                }
            }
        });
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditImage.this, CaptureActivity.class);
        startActivity(intent);
    }

}
