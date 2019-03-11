package dev.master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    private Button  open_add_place;
    private Button  open_placees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        open_add_place=findViewById(R.id.openaddplace);
        open_placees=findViewById(R.id.places);


        open_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity( new Intent(HomeActivity.this,pick_location.class));



                //
                //  mCtx.sendBroadcast(new Intent("start.fragment_myitem.action").putExtra("id", user.getId()));
            }
        });



        open_placees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity( new Intent(HomeActivity.this,MainActivity.class));


                //
                //  mCtx.sendBroadcast(new Intent("start.fragment_myitem.action").putExtra("id", user.getId()));
            }
        });

    }
}
