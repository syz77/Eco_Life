package com.laguna.sergio.ecolife;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lati=0;
    double longi=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //ArrayList<String>
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
        ArrayList<String> fechas = (ArrayList<String>) getIntent().getStringArrayListExtra("fechas");
        ArrayList<String> listlat = (ArrayList<String>) getIntent().getStringArrayListExtra("latitudes");
        ArrayList<String> listlong = (ArrayList<String>) getIntent().getStringArrayListExtra("longitudes");
        ArrayList<String> cuotas = (ArrayList<String>) getIntent().getStringArrayListExtra("cuotas");
        mMap = googleMap;

        lati = Double.parseDouble(listlat.get(0));
        longi = Double.parseDouble(listlong.get(0));
        LatLng sydney = new LatLng(lati, longi);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
        //int tamanio = fechas.size();
        if (fechas.size()==1){

        }else{
            Toast.makeText(MapsActivity.this, "Nro de cobros: "+ Integer.toString(fechas.size()), Toast.LENGTH_LONG).show();
        }
        for (int i=0; fechas.size()>i;i++) {
            // Add a marker in Sydney and move the camera
            lati = Double.parseDouble(listlat.get(i));
            longi = Double.parseDouble(listlong.get(i));
            LatLng cobros = new LatLng(lati, longi);
            mMap.addMarker(new MarkerOptions().position(cobros).title("cuota:"+cuotas.get(i)+"  fecha:"+fechas.get(i)));

        }
    }
}
