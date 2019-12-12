package com.example.grocerymark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        toolbar = findViewById(R.id.toolbar);
        //setsupportactionbar(toolbar);

        navigationView = findViewById(R.id.navi_view_map);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Intent signOut = new Intent(MapsActivity.this, HomeScreen.class);
                    startActivity(signOut);
                }else if(id == R.id.user){
//                    Intent cart = new Intent(MapsActivity.this, Cart.class);
//                    startActivity(cart);
                }
                else if(id == R.id.Cart){
                    Intent map = new Intent(MapsActivity.this, MapsActivity.class);
                    startActivity(map);
                }else if(id == R.id.Stores){
                    Intent stores = new Intent(MapsActivity.this, MapsActivity.class);
                    startActivity(stores);
                }
                else if(id == R.id.logout){
                   logout();
                }
                return true;
            }
        });

        drawer = findViewById(R.id.drawerMap);
        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stores_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String storeName = spinner.getSelectedItem().toString();

                Log.e("Selected item : ", storeName);

                if(storeName == "Search your nearest store")
                {
// do nothing
                    Toast.makeText(MapsActivity.this,"In stores part",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MapsActivity.this,"In other part",Toast.LENGTH_SHORT).show();

                    setlocation(storeName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    public void setlocation(String storeName) {
        final Geocoder coder = new Geocoder(getApplicationContext());

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // clear previous map marker
        mMap.clear();
        MarkerOptions markerOptions_1 = new MarkerOptions();

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);


        InfoWindowData info_1 = new InfoWindowData();

        // show relative current location
        LatLng my_location = new LatLng(43.785464, -79.226620);

        markerOptions_1.position(my_location).title("Centennial College").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("current_location",140,140)));


        info_1.setPlaceName("Centennial College - Progress Campus");
        info_1.setPlaceAddress("941 Progress Avenue, Toronto Ontario, Canada, M1G 3T8");
        mMap.setInfoWindowAdapter(customInfoWindow);

        final Marker m_1 = mMap.addMarker(markerOptions_1);
        m_1.setTag(info_1);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location, 10.2f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                m_1.showInfoWindow();
                return false;
            }
        });

        String[] elements = {"Scarborough", "Markham", "North York", "Toronto"};
        for (String s: elements) {
            //Do your stuff here
            Log.e("Selected item : ", storeName + " " + s);

            String placeName = storeName + " " + s;

            try {
                //using geocoder object getting maximum 5 addresses for given place name / address
                List<Address> geocodeResults = coder.getFromLocationName(placeName, 3);
                Iterator<Address> locations = geocodeResults.iterator();

                // get location names and relative addrress and show them on google map
                String locInfo = "";
                while (locations.hasNext()) {
                    Address loc = locations.next();

                    LatLng store = new LatLng(loc.getLatitude(), loc.getLongitude());
                    int addIdx = loc.getMaxAddressLineIndex();
                    for (int idx = 0; idx <= addIdx; idx++)
                    {
                        String addLine = loc.getAddressLine(idx);
                        locInfo = String.format("%s", addLine);
                    }


                    //mMap.addMarker(new MarkerOptions().position(store).title(brandName +", "+ locInfo));

                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(store, 10.2f));


                    MarkerOptions markerOptions = new MarkerOptions();

                    markerOptions.position(store)
                            .title(storeName +", "+ locInfo);
                    InfoWindowData info = new InfoWindowData();
                    info.setPlaceName(storeName);
                    info.setPlaceAddress(locInfo);
                    mMap.setInfoWindowAdapter(customInfoWindow);

                    final Marker m = mMap.addMarker(markerOptions);
                    m.setTag(info);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(store));

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            m.showInfoWindow();
                            return false;
                        }
                    });
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Failed to get location info " +e,Toast.LENGTH_LONG).show();
            }

        }

//
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();

        Intent signOut = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(signOut);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng my_location = new LatLng(43.785464, -79.226620);

        MarkerOptions markerOptions_1 = new MarkerOptions();

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);


        InfoWindowData info_1 = new InfoWindowData();

        // show relative current location

        markerOptions_1.position(my_location).title("Centennial College").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("current_location",140,140)));


        info_1.setPlaceName("Centennial College - Progress Campus");
        info_1.setPlaceAddress("941 Progress Avenue, Toronto Ontario, Canada, M1G 3T8");
        mMap.setInfoWindowAdapter(customInfoWindow);

        final Marker m_1 = mMap.addMarker(markerOptions_1);
        m_1.setTag(info_1);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(my_location, 10.2f));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                m_1.showInfoWindow();
                return false;
            }
        });
    }

    // function for setting the icon size for current location
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
