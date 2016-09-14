package ict.posco.com.ictapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ict.posco.com.model.flower;

public class flowerAdapter extends ArrayAdapter<flower> {
    private Context context;
    private List<flower> flowerList;

    public flowerAdapter(Context context, int resource, List<flower> objects) {
        super(context, resource, objects);
        this.context = context;
        flowerList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower,parent,false);

        flower fl = flowerList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView2);
        tv.setText(fl.getName());
        return view;
    }
}
