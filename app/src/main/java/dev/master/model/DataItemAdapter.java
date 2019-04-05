package dev.master.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dev.master.DbHelper;
import dev.master.MapsActivity;
import dev.master.R;
import dev.master.locations_list;


public class DataItemAdapter extends ArrayAdapter<location> {

    List<location> mDataItems;
    LayoutInflater mInflater;


    private DbHelper db;

    SQLiteDatabase database;


    public DataItemAdapter(Context context, List<location> objects){
        super(context, R.layout.list_item, objects);

        mDataItems = objects;
        mInflater = LayoutInflater.from(context);

        db = new DbHelper(context);
        database= db.getWritableDatabase();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        ImageView delete = (ImageView) convertView.findViewById(R.id.delete);


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


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(delete(String.valueOf( l.getName())))
                                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete this?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });



        //ivFlag.setImageResource(getImageDrawable(item.getType()));


//        return super.getView(position, convertView, parent);
        return convertView;
    }


    public boolean delete(String id)
    {
        return database.delete("places", "_id" + "=" + id, null) > 0;
    }
    /*----------------------- private helper ------------------- */

}
