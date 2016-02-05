package com.example.doranwalsten.simpleimagedraw;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by doranwalsten on 1/29/16.
 */
public class SliderList extends ArrayAdapter<String> {

    private final Activity context;
    private String[] parameter_names;
    private ArrayList<Double[]> parameter_bounds;
    private boolean edited = false;

    public SliderList(Activity context, String[] names,ArrayList<Double[]> bounds) {
        super(context, R.layout.activity_edit_image, names);
        this.context = context;
        this.parameter_names = names;
        this.parameter_bounds = bounds;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.slider_list, null, true);
        final TextView parameter_title = (TextView) rowView.findViewById(R.id.parameter);
        SeekBar bar_thing = (SeekBar) rowView.findViewById(R.id.bar);
        final double range = parameter_bounds.get(position)[1] - parameter_bounds.get(position)[0];
        final int[] progressChanged = {bar_thing.getProgress()};
        bar_thing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                edited = true;
                progressChanged[0] = seekBar.getProgress();
                double display_value = progressChanged[0]/100. * range + parameter_bounds.get(position)[0];
                parameter_title.setText(String.format("%s : %.2f", parameter_names[position],display_value));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

            //Initialize the parameter title and value

        //Initialize the seekbar
        if (!edited) {
            parameter_title.setText(String.format("%s : %.2f", parameter_names[position],0.5*range + parameter_bounds.get(position)[0]));
        }

        //Table Formatting
        Resources res = this.context.getResources();
        rowView.setBackgroundColor(res.getColor(R.color.white));

        return rowView;
    }

}
