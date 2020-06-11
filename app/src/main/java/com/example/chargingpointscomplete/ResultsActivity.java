package com.example.chargingpointscomplete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chargingpointscomplete.Api.Feed;
import com.example.chargingpointscomplete.Api.JsonPlaceHolderApi;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDevice;
import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceComplete;
import com.example.chargingpointscomplete.ChargeDeviceSetup.Connector;
import com.example.chargingpointscomplete.Database.ChargeViewModel;
import com.example.chargingpointscomplete.RecyclerView.RecyclerViewAdapter;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResultsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = "ResultsActivity";
    // Permission vars
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private boolean mLocationPermissionGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    // Init
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ChargeViewModel chargeViewModel;


    public LiveData<List<ChargeDeviceComplete>> chargeDeviceDBS;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        if (isServicesOK()) {
            // Clear ArrayList in the case of multiple searches
            chargeDeviceDBS = null;
            getLocationPermission();
            // Retrofit api get request and insert to database
            getDataApi();

            // Initialise recycler view and set adapter, with the LiveData list model the adapter
            // can be called later in a loop which allows multiple updates
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(ResultsActivity.this));
            final RecyclerViewAdapter adapter = new RecyclerViewAdapter(chargeDeviceDBS);
            recyclerView.setAdapter(adapter);

            chargeViewModel = ViewModelProviders.of(ResultsActivity.this).get(ChargeViewModel.class);
            adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(ChargeDeviceComplete chargeDeviceDBS) {

                    Intent getIntent = getIntent();

                    final double latt = getIntent.getDoubleExtra("latitude", 0);
                    final double longg = getIntent.getDoubleExtra("longitude", 0);

                    Intent intent = new Intent(ResultsActivity.this, SingleChargeDetailsActivity.class);
                    ChargeDeviceComplete chargeDeviceDB = chargeDeviceDBS;
                    // Selected chargeDevice is converted to json string for easy intent. Json was already imported
                    // so seemed the easiest option
                    String chargeDeviceString = new Gson().toJson(chargeDeviceDB);
                    intent.putExtra("Json", chargeDeviceString);
                    intent.putExtra("latitude", latt);
                    intent.putExtra("longitude", longg);
                    startActivity(intent);
                }
            });

            chargeViewModel.getAllChargeOrder().observe(ResultsActivity.this, new Observer<List<ChargeDeviceComplete>>() {
                @Override
                public void onChanged(List<ChargeDeviceComplete> chargeDeviceDBS) {
                    // Update RecyclerView
                    adapter.setmChargeDevices(chargeDeviceDBS);
                    // Remove markers from map in case of multiple searches
                    mMap.clear();

                    for (int i = 1; i < chargeDeviceDBS.size(); i++) {
                        LatLng latLng = new LatLng(Double.valueOf(chargeDeviceDBS.get(i).getLatitude()), Double.valueOf(chargeDeviceDBS.get(i).getLongitude()));
                        // Markers on map , will be placed every search and wiped when new search is done
                        placeMarkers(latLng);
                    }
                }
            });
        }
    }// End of onCreate

    private void getDataApi() {

        Intent intent = getIntent();
        // Variables to enter into the get request
        // Connector id
        String[] connID = intent.getStringArrayExtra("connID");
        String id = connID[0];
        String mileageRadius = intent.getStringExtra("mileageRadius");
        final double latt = intent.getDoubleExtra("latitude", 0);
        final double longg = intent.getDoubleExtra("longitude", 0);


        // Get data from Gov api and convert to JSON , then add to mChargeDevice ArrayList
        // Retrofit is good for complicated data sets. I have explained the the charge device class,
        // but it basically looks for variables of the same name and creates and object for you.
        // With the data set i selected this saved me a lot of time when starting my project
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://chargepoints.dft.gov.uk/api/retrieve/registry/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi ukGovAPI = retrofit.create(JsonPlaceHolderApi.class);
        // Variables to pass to api to allow a much more streamlined search
        Call<Feed> call = ukGovAPI.getFeed(id, latt, longg, mileageRadius);
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {

                ArrayList<ChargeDevice> mChargeDevices;
                mChargeDevices = response.body().getChargeDevice();
                chargeViewModel.deleteAllCharge();

                // I did this to bring all variables to the same level and have nothing nested
                // Allowed for easier database entry
                for (int i = 1; i < mChargeDevices.size(); i++) {
                    // Base chargeDevice variables
                    final String chargeDeviceName = mChargeDevices.get(i).getChargeDeviceName();
                    final String locationType = mChargeDevices.get(i).getLocationType();
                    final String accessible24Hours = mChargeDevices.get(i).getAccessible24Hours();
                    final String chargeDeviceStatus = mChargeDevices.get(i).getChargeDeviceStatus();
                    final String paymentRequiredFlag = mChargeDevices.get(i).getPaymentRequired();
                    final String paymentDetails = mChargeDevices.get(i).getPaymentDetails();
                    final String subscriptionRequiredFlag = mChargeDevices.get(i).getSubscriptionRequired();
                    final String subscriptionDetails = mChargeDevices.get(i).getSubscriptionDetails();
                    final String parkingFeesFlag = mChargeDevices.get(i).getParkingFees();
                    final String parkingFeesDetails = mChargeDevices.get(i).getParkingFeesDetails();
                    final String parkingFeesUrl = mChargeDevices.get(i).getParkingFeesUrl();
                    // Connector array
                    final ArrayList<Connector> connector = mChargeDevices.get(i).getConnector();
                    // Charge device owner
                    final String deviceOwnerAddress = mChargeDevices.get(i).getDeviceOwner().getOrganisationName();
                    final String deviceOwnerWebsite = mChargeDevices.get(i).getDeviceOwner().getWebsite();
                    final String deviceOwnerTelephoneNo = mChargeDevices.get(i).getDeviceOwner().getTelephoneNo();
                    // Address variables
                    final String longitude = mChargeDevices.get(i).getChargeDeviceLocation().getLongitude();
                    final String latitude = mChargeDevices.get(i).getChargeDeviceLocation().getLatitude();
                    final String postcode = mChargeDevices.get(i).getChargeDeviceLocation().getAddress().getPostCode();
                    final String buildingName = mChargeDevices.get(i).getChargeDeviceLocation().getAddress().getBuildingName();
                    final String thoroughfare = mChargeDevices.get(i).getChargeDeviceLocation().getAddress().getThoroughfare();
                    final String postTown = mChargeDevices.get(i).getChargeDeviceLocation().getAddress().getPostTown();
                    final String county = mChargeDevices.get(i).getChargeDeviceLocation().getAddress().getCounty();

                    // The user lat and long was put into the database as well as to allow 'Roomdb' to do a query
                    // and calculate the closest point
                    final double userLat = latt;
                    final double userLong = longg;

                    ChargeDeviceComplete chargeDeviceDBS = new ChargeDeviceComplete(chargeDeviceName, locationType, accessible24Hours,
                            chargeDeviceStatus, paymentRequiredFlag, paymentDetails, subscriptionRequiredFlag,
                            subscriptionDetails, parkingFeesFlag, parkingFeesDetails, parkingFeesUrl, connector,
                            deviceOwnerAddress, deviceOwnerWebsite, deviceOwnerTelephoneNo, userLat, latitude, userLong, longitude,
                            postcode, buildingName, thoroughfare, postTown, county);

                    // Insert to database
                    chargeViewModel.insert(chargeDeviceDBS);
                }
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
            }
        });
    }


    private void initMap() {
        Log.d(TAG, "initMap: initialising map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMapResults);
        mapFragment.getMapAsync(ResultsActivity.this);
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
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    11f);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(ResultsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException:" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ",lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void placeMarkers(LatLng latLng) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng);
        mMap.addMarker(options);
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
                getDeviceLocation();
                initMap();
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
                    initMap();
                }
            }
        }

    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ResultsActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            // Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can fix it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ResultsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
