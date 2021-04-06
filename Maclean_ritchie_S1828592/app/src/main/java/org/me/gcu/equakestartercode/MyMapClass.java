//MacLean_Ritchie_S1828592
package org.me.gcu.equakestartercode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.fragment.app.FragmentActivity;

public class MyMapClass extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap myMap;
    private Button backButton;
   @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        backButton = (Button)findViewById(R.id.button);
        backButton.setOnClickListener(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

       myMap = googleMap;
       CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(55, 4.2 ));
       CameraUpdateFactory.zoomTo(20);
       myMap.moveCamera(point);

       for(int i = 0; i < ArrayStore.storeGeoLat.size(); i++)
       {
           LatLng quakePoint = new LatLng(Float.parseFloat(ArrayStore.storeGeoLat.get(i)), Float.parseFloat(ArrayStore.storeGeoLong.get(i)));

           if(Double.valueOf(ArrayStore.storeMag.get(i)) < 1)
           {
               myMap.addMarker(new MarkerOptions().position(quakePoint).title(ArrayStore.storeTitles.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
           }

           if(Double.valueOf(ArrayStore.storeMag.get(i)) >= 1 && Double.valueOf(ArrayStore.storeMag.get(i)) < 2)
           {
               myMap.addMarker(new MarkerOptions().position(quakePoint).title(ArrayStore.storeTitles.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
           }

           if(Double.valueOf(ArrayStore.storeMag.get(i)) >= 2 && Double.valueOf(ArrayStore.storeMag.get(i)) < 3)
           {
               myMap.addMarker(new MarkerOptions().position(quakePoint).title(ArrayStore.storeTitles.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
           }

           if(Double.valueOf(ArrayStore.storeMag.get(i)) >= 3)
           {
               myMap.addMarker(new MarkerOptions().position(quakePoint).title(ArrayStore.storeTitles.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
           }
       }

    }

    @Override
    public void onClick(View view) {
       myMap.clear();
       ArrayStore.storeTitles.clear();
       ArrayStore.storeDescription.clear();
       ArrayStore.storeCategory.clear();
       ArrayStore.storeLink.clear();
       ArrayStore.storePubDate.clear();
       ArrayStore.storeGeoLat.clear();
       ArrayStore.storeGeoLong.clear();
       ArrayStore.storeMag.clear();
       Intent mainMenuIntent = new Intent(this, MainActivity.class);
       startActivity(mainMenuIntent);
    }
}
