package com.tw.awayday.citizensassist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.tw.awayday.citizensassist.Models.IssueAddress;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.google.android.gms.common.api.GoogleApiClient.Builder;
import static com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import static com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import static com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import static com.tw.awayday.citizensassist.UserMessages.OPENING_MAPS;
import static com.tw.awayday.citizensassist.LocationFetcherConstants.*;


import static com.tw.awayday.citizensassist.UserMessages.Tag_Location;

public class TagLocationActivity extends FragmentActivity implements LocationListener,
        ConnectionCallbacks, OnConnectionFailedListener, OnMapReadyCallback, OnMarkerDragListener {


    private GoogleMap map;
    private Geocoder geocoder;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;


    Button sendLocationButton;
    TextView locationTextView;
    LocationRequest locationRequest;
    GoogleApiClient googleApiClient;
    Location currentLocation;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, ON_CREATE);
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }

        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), Tag_Location, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.CENTER_VERTICAL;
        view.setLayoutParams(params);
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snack.show();

        createLocationRequest();
        googleApiClient = new Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        geocoder = new Geocoder(this, Locale.getDefault());
        setContentView(R.layout.activity_tag_location);

        sendLocationButton = (Button) findViewById(R.id.send_my_location);
        locationTextView = (TextView) findViewById(R.id.location_view);

        sendLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent resultIntent = new Intent();
                LatLng position = marker.getPosition();
                resultIntent.putExtra(POSITION, position);
                try {
                    resultIntent.putExtra(ISSUE_ADDRESS, getIssueAddress(
                            geocoder.getFromLocation(position.latitude, position.longitude, 1)));
                } catch (IOException ignored) {
                }
                setResult(Activity.RESULT_OK, resultIntent);
                startActivity(new Intent(TagLocationActivity.this, CategorySelectionActivity.class));
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        return ConnectionResult.SUCCESS == status;
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                updateTextViewToDisplayCurrentLocation(latLng);
                marker.setPosition(latLng);
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(14));
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        makeText(getApplicationContext(), OPENING_MAPS, LENGTH_SHORT).show();

        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, ON_CONNECTED_IS_CONNECTED + googleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        boolean finePermissionNotGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarsePermissionNotGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (finePermissionNotGranted && coarsePermissionNotGranted) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        showCurrentLocationOnMap();
    }

    private void showCurrentLocationOnMap() {
        if (currentLocation == null) {
            return;
        }

        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        updateTextViewToDisplayCurrentLocation(currentLatLng);
        map.setMapType(MAP_TYPE_NORMAL);
        map.clear();
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        boolean finePermissionNotGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        boolean coarsePermissionNotGranted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED;

        if (finePermissionNotGranted && coarsePermissionNotGranted) {
            return;
        }

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16));
        marker = map.addMarker(new MarkerOptions()
                .position(currentLatLng)
                .title(CURRENT_LOCATION)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                .draggable(true));
        map.setOnMarkerDragListener(this);
    }

    private IssueAddress getIssueAddress(List<Address> addresses) {
        return new IssueAddress().setAddressLine(addresses.get(0).getAddressLine(0))
                .setCity(addresses.get(0).getLocality())
                .setCountry(addresses.get(0).getCountryName())
                .setState(addresses.get(0).getAdminArea());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, CONNECTION_FAILED + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, FIRING_ON_LOCATION_CHANGED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        Log.d(TAG, LOCATION_UPDATE_STOPPED);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, LOCATION_UPDATE_RESUMED);
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
        updateTextViewToDisplayCurrentLocation(marker.getPosition());
    }

    private void updateTextViewToDisplayCurrentLocation(LatLng position) {
        try {
            IssueAddress issueAddress = getIssueAddress(geocoder.getFromLocation(position.latitude,
                    position.longitude, 1));
            locationTextView.setText(issueAddress.toString());
        } catch (IOException ignored) {
        }
    }
}
