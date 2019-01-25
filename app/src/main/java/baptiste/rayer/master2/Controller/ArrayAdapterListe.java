package baptiste.rayer.master2.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import baptiste.rayer.master2.R;
import baptiste.rayer.master2.model.Film;

/**
 * Created by Batra on 01/01/2018.
 */

public class ArrayAdapterListe extends ArrayAdapter {
    public ArrayAdapterListe(@NonNull Context context, @NonNull List<Film> resource) {
        super(context,R.layout.list_array_adapter,resource);
    }

    @Override
    public View getView (int position, View view, ViewGroup parent){
        // Get the data item for this position
        Log.d("ArrayAdapter :", "???? ");



        Film row = (Film) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_array_adapter, parent, false);
        }
        // Lookup view for data population
        TextView nom = (TextView) view.findViewById(R.id.textView);
        ImageView iv = (ImageView) view.findViewById(R.id.activity_list_items_img);
        // Populate the data into the template view using the data object
        nom.setText(row.getNom());
        LinearLayout item = (LinearLayout) view.findViewById(R.id.layout_list);

        if(position % 2 == 0){
            item.setBackgroundColor(Color.WHITE);
        }else{
            item.setBackgroundColor(Color.GRAY);
        }
        if(row.getImage() != null){
            iv.setImageBitmap(row.getImage());
        }
        // Return the completed view to render on screen
        return view;
    }
}
