package dev.master;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.master.model.DataItemAdapter;
import dev.master.model.location;

public class locations_list extends AppCompatActivity {


    private List<location> notesList = new ArrayList<>();

    private DbHelper db;

    SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);
        database= db.getWritableDatabase();

        notesList.addAll(db.getsavedlocations());




        ListView listView =(ListView) findViewById(android.R.id.list);




//        Collections.sort(rowItems, new Comparator<DataItem>() {
//            @Override
//            public int compare(DataItem i1, DataItem i2) {
//                return i1.getName().compareTo(i2.getName());
//            }
//        });
        DataItemAdapter adapter = new DataItemAdapter(this, notesList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, final long id) {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if(delete(String.valueOf(id)))
                                    Toast.makeText(locations_list.this, "Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(locations_list.this);
                builder.setMessage("Are you sure you want to delete this?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

    }



    public boolean delete(String id)
    {
        return database.delete("places", "_id" + "=" + id, null) > 0;
    }
}
