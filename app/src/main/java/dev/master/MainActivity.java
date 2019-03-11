package dev.master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import dev.master.model.DataItemAdapter;
import dev.master.model.location;

public class MainActivity extends AppCompatActivity {


    private List<location> notesList = new ArrayList<>();

    private DbHelper db;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DbHelper(this);

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


    }
}
