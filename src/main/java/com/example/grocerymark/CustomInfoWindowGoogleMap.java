package com.example.grocerymark;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }



    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custominfowindow, null);
        TextView Place = view.findViewById(R.id.placeName);
        TextView Address = view.findViewById(R.id.placeAddress);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        Place.setText(infoWindowData.getPlaceName());
        Address.setText(infoWindowData.getPlaceAddress());

        return view;
    }
}
