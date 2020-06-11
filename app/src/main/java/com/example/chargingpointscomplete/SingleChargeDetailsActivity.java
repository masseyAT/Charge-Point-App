package com.example.chargingpointscomplete;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import com.example.chargingpointscomplete.ChargeDeviceSetup.Connector;
import com.example.chargingpointscomplete.RecyclerView.DetailsAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Objects;

public class SingleChargeDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "ChargeDetails";

    RecyclerView recyclerView;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private boolean mLocationPermissionGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    // Init
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    // UI variables
    private TextView chargeDeviceName;
    private TextView postCode;
    private TextView postTown;
    private TextView paymentReq;
    private TextView access;
    private TextView ownerName;
    private TextView ownerUrl;
    // In service pictures
    int images [] = {R.drawable.greentick, R.drawable.redcross};

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: map is ready");

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (isServicesOK()) {
            getLocationPermission();
            // Init and link up all layout textViews
            recyclerView = findViewById(R.id.connectorList);
            chargeDeviceName = findViewById(R.id.chargeDeviceName);
            postTown = findViewById(R.id.postTown);
            postCode = findViewById(R.id.postCode);
            paymentReq = findViewById(R.id.paymentRequired);
            access = findViewById(R.id.accessible);
            ownerName = findViewById(R.id.ownerName);
            ownerUrl = findViewById(R.id.ownerUrl);


            Intent intent = getIntent();
            // Retrieve and unpack into a chargeDevice object from json
            ChargeDeviceComplete selectedChargeDevice = new Gson().fromJson(intent.getStringExtra("Json"), ChargeDeviceComplete.class);
            final double latt = intent.getDoubleExtra("latitude", 0);
            final double longg = intent.getDoubleExtra("longitude", 0);

            ArrayList<Connector> connector = selectedChargeDevice.getConnector();

            DetailsAdapter detailsAdapter = new DetailsAdapter(this, connector, images);
            recyclerView.setAdapter(detailsAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            chargeDeviceName.setText(selectedChargeDevice.getChargeDeviceName());
            postTown.setText(selectedChargeDevice.getPostTown());
            postCode.setText(selectedChargeDevice.getPostCode());
            access.setText("Accessible 24 hours: "+selectedChargeDevice.getAccessible24Hours());
            ownerName.setText(selectedChargeDevice.getDeviceOwnerAddress());
            ownerUrl.setText(selectedChargeDevice.getDeviceOwnerWebsite());
            ownerUrl.setMovementMethod(LinkMovementMethod.getInstance());

            String payment = selectedChargeDevice.getPaymentRequired();
            if (Objects.equals(payment, "false")) {
                payment = "Free to use";
            } else {
                payment = "Payment Required";
            }
            paymentReq.setText("Payment: " + payment);
        }
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SingleChargeDetailsActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can fix it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(SingleChargeDetailsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void initMap() {
        Log.d(TAG, "initMap: initialising map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_details);

        mapFragment.getMapAsync(SingleChargeDetailsActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "gettingLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                Log.d(TAG, "getLocationPermission: a ok !!!!!!!!!!!");
                initMap();
                getDeviceLocation();

            } else {
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }

                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    mLocationPermissionGranted = true;
                }
            }
        }

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Log.d(TAG, "getDeviceLocation: got this far!!!!");

                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Intent intent = getIntent();
                            ChargeDeviceComplete selectedChargeDevice = new Gson().fromJson(intent.getStringExtra("Json"), ChargeDeviceComplete.class);

                            double Latitude = Double.valueOf(selectedChargeDevice.getLatitude());
                            double Longitude = Double.valueOf(selectedChargeDevice.getLongitude());

                            LatLng latLng = new LatLng(Latitude, Longitude);
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    10f);

                            placeMarkers(latLng);


                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(SingleChargeDetailsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException:" + e.getMessage());
        }
    }

    private void placeMarkers(LatLng latLng) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng);
        mMap.addMarker(options);
    }


    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ",lng: " + latLng.longitude);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
    }
}


