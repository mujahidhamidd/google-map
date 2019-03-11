package dev.master.model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import dev.master.MapsActivity;
import dev.master.R;


public class DataItemAdapter extends ArrayAdapter<location> {

    List<location> mDataItems;
    LayoutInflater mInflater;



    public DataItemAdapter(Context context, List<location> objects){
        super(context, R.layout.list_item, objects);

        mDataItems = objects;
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);


        final location l = mDataItems.get(position);
        tvName.setText(l.getName());



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent itent = new Intent(getContext(), MapsActivity.class);
                itent.putExtra("title", l.getName());
                itent.putExtra("lat", l.getLatitude());
                itent.putExtra("long", l.getLongitude());

                getContext().startActivity(itent);}
        });


        //ivFlag.setImageResource(getImageDrawable(item.getType()));


//        return super.getView(position, convertView, parent);
        return convertView;
    }

    /*----------------------- private helper ------------------- */

}
