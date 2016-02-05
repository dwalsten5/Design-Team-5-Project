package com.example.doranwalsten.simpleimagedraw;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by doranwalsten on 2/4/16.
 */
public class RhomboidActivity extends AppCompatActivity {

    //Objects in the view that are important to reference
    ImageView mImageView;
    SeekBar ratio;
    SeekBar alpha;
    SeekBar beta;
    RhomboidFlap new_flap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhomboid);//
        mImageView = (ImageView) findViewById(R.id.rhom_Image);
        ratio = (SeekBar) findViewById(R.id.seekBar_1);
        alpha = (SeekBar) findViewById(R.id.seekBar_2);
        beta = (SeekBar) findViewById(R.id.seekBar_3);

        final Point displacement = new Point();
        RelativeLayout home = (RelativeLayout) findViewById(R.id.Rhom_layout);
        Bitmap back = (Bitmap) this.getIntent().getExtras().get("Background");
        mImageView.setImageBitmap(back);

        mImageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                            View draggedImage = (View) event.getLocalState();
                            if (draggedImage.getClass() == RhomboidFlap.class) {
                                RhomboidFlap draggedFlap = (RhomboidFlap) event.getLocalState();
                                displacement.set((int) event.getX() - draggedFlap.getCenter().x, (int) event.getY() - 150 - draggedFlap.getCenter().y);
                                Log.i("Drag", String.format("X Displacement Calculated as %d, %d", (int) event.getX(), draggedFlap.getCenter().x));
                                Log.i("Drag", String.format("Y Displacement Calculated as %d, %d", (int) event.getY(), draggedFlap.getCenter().y));
                            }

                            return true;
                        } else {
                            return false;
                        }
                    case DragEvent.ACTION_DROP:
                        View draggedImage = (View) event.getLocalState();
                        if (draggedImage.getClass() == RhomboidFlap.class) {
                            RhomboidFlap draggedFlap = (RhomboidFlap) event.getLocalState();
                            Log.i("Drag", String.format("Current Center: %d, %d", (int) event.getX(), (int) event.getY()));
                            draggedFlap.setCenter((int) event.getX() - displacement.x, (int) event.getY() - displacement.y);
                            Log.i("Drag", String.format("Displacement Calculated as %d, %d", displacement.x, displacement.y));
                            Log.i("Drag", String.format("Updated Center: %d, %d", draggedFlap.getCenter().x, draggedFlap.getCenter().y));
                            draggedFlap.invalidate();
                            Log.i("Drag", "Dropped Here");
                            draggedFlap.setVisibility(View.VISIBLE);

                        } else {
                            draggedImage.setX(event.getX() - draggedImage.getWidth() / 2);
                            draggedImage.setY(event.getY() - draggedImage.getHeight() / 2);
                            draggedImage.setVisibility(View.VISIBLE);
                        }

                        //Try dropping a new image at the point

                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });

        new_flap = new RhomboidFlap(this.getBaseContext(),200,200);
        //ImageView viewSeen = new_flap.returnImage();
        new_flap.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData = ClipData.newPlainText("", "");

                //Use Bitmap to create Shadow
                //v.setDrawingCacheEnabled(true);
                //Bitmap viewCapture = Bitmap.createScaledBitmap(v.getDrawingCache(),250,250,true);
                //FlapDragShadowBuilder shadowBuilder = new FlapDragShadowBuilder(viewCapture);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder();
                v.startDrag(clipData, shadowBuilder, v, 0);
                v.setVisibility(View.GONE);
                return true;
            }
        });
        home.addView(new_flap);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RhomboidActivity.this, EditImage.class);
        intent.putExtra("Background",((BitmapDrawable) mImageView.getDrawable()).getBitmap());
        //Need to somehow save the drawn flap to the UI, but not as a RhomboidFlap
        //Design returnImage to get the cropping right, identify the location on the screen where
        //flap is

        startActivity(intent);
    }
}
