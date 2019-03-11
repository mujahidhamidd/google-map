package dev.master;

import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pick_location extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private EditText mSearchText;
    private Button addto;
    private ImageView mSearch;
    private Marker myMarker;

    DbHelper mDbHelper;
    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mDbHelper = new DbHelper(this);
        db = mDbHelper.getWritableDatabase();


        setContentView(R.layout.activity_pick_location);
        mSearchText = (EditText) findViewById(R.id.input_search);
        addto = (Button) findViewById(R.id.buttonaddto);
        mSearch = (ImageView) findViewById(R.id.ic_magnify);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                geoLocate();

                //
                //  mCtx.sendBroadcast(new Intent("start.fragment_myitem.action").putExtra("id", user.getId()));
            }
        });






    }



    private void geoLocate(){


        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(pick_location.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){

        }

        if(list.size() > 0){
            Address address = list.get(0);


            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 15f,
                    address.getAddressLine(0));
        }
    }



    private void moveCamera(LatLng latLng, float zoom, String title){
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
       // googleMap.setOnMarkerClickListener(this);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
//                int position = (int)(marker.getTag());

                addto.setVisibility(View.VISIBLE);

                addto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                          savetodatabase(marker.getTitle(),marker.getPosition().latitude,marker.getPosition().longitude);


                        //
                        //  mCtx.sendBroadcast(new Intent("start.fragment_myitem.action").putExtra("id", user.getId()));
                    }
                });

                //Using position get Value from arraylist
                return false;
            }
        });
    }

    private void savetodatabase(String title, double latitude, double longitude) {




        mDbHelper.addplace(title,String.valueOf(latitude),String.valueOf(longitude));

        // Snack Bar to show success message that record saved successfully
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }


}
