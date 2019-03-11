package dev.master;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int position;

    boolean isMapMode = true;
    String title,lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        title = getIntent().getStringExtra("title");
        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("long");

    }

    public void onSwitchMode(View v){
        isMapMode = !isMapMode;
        Button b = (Button)v;
        if(isMapMode){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            b.setText("SATELLITE MODE");
        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            b.setText("MAP MODE");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        isMapMode = true;
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

            LatLng rest = new LatLng(Double.valueOf(lat),Double.valueOf(lng));
            mMap.addMarker(new MarkerOptions().position(rest).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(rest));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));



    }


}
