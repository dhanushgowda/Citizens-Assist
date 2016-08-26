package com.tw.awayday.citizensassist;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationFetcherActivity extends FragmentActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;
    private Geocoder geocoder;
    private List<Address> addresses;
    private boolean dragged;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private double latitude;
    private double longitude;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate ...............................");
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        geocoder = new Geocoder(this, Locale.getDefault());
        setContentView(R.layout.activity_location_fetcher);

        btnFusedLocation = (Button) findViewById(R.id.show_location);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        btnFusedLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Position", marker.getPosition());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map123);
        mapFragment.getMapAsync(this);

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                latitude = mCurrentLocation.getLatitude();
                longitude = mCurrentLocation.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                marker.setPosition(latLng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        showCurrentLocationOnMap();
    }

    private void showCurrentLocationOnMap() {
        if (null != mCurrentLocation && !dragged) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
            String address = "";
            String city = "";
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();
            } catch (IOException e) {
                e.printStackTrace();
            }

            tvLocation.setText("At Time: " + mLastUpdateTime + "\n" +
                    "Address: " + address + "\n" +
                    "City: " + city + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy());
            LatLng point = new LatLng(latitude, longitude);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.clear();
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 16));
            marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title("Current Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .draggable(true));
            mMap.setOnMarkerDragListener(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
//        mCurrentLocation = location;
//        showCurrentLocationOnMap();
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed ....................");
        }
    }


    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng position = marker.getPosition();
        longitude = position.longitude;
        latitude = position.latitude;
        dragged = true;
    }
}
