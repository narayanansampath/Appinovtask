package com.scsvmv.ryan.appinovtask.ui.mapsmodule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.scsvmv.ryan.appinovtask.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private LatLng currentLatLng;
    FloatingActionButton fabmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        attachLocationListener();
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        findViews(view);
        fabmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location loc = getLastBestLocation();
                double loc_lat = loc.getLatitude();
                double loc_lon = loc.getLongitude();
                String uri = "http://maps.google.com/maps?daddr=" +loc_lat+","+loc_lon;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = "Here is my location";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;


    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Location lastKnownLocation = getLastBestLocation();
        updateCurrentPosition(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
    }

    private void findViews(View container){
        this.fabmap  = container.findViewById(R.id.fabmap);
    }

    @SuppressLint("MissingPermission")
    private Location getLastBestLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }

    private void updateCurrentPosition(double lat, double lng) {
        currentLatLng = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Your Location")).showInfoWindow();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17));
    }


    @SuppressLint("MissingPermission")
    private void attachLocationListener() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                updateCurrentPosition(latitude, longitude);
            }

            @Override
            public void onProviderDisabled(String provider) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10,
                locationListener);
    }
}