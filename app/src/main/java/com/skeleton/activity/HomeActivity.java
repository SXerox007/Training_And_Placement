package com.skeleton.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.skeleton.MyApplication;
import com.skeleton.R;
import com.skeleton.fragment.DrawerMenuFragment;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.retrofit.RetrofitUtils;
import com.skeleton.util.currentlocationfetcher.LocationFetcher;
import com.skeleton.util.currentlocationfetcher.LocationFetcherCallBack;
import com.skeleton.util.customview.ProgressDialog;
import com.skeleton.util.dialog.CustomAlertDialog;
import com.skeleton.util.googledirections.DirectionPolylinesCallback;
import com.skeleton.util.googledirections.GoogleDirectionsCallback;
import com.skeleton.util.googledirections.GoogleDirectionsMap;
import com.skeleton.util.googledirections.googledirectionmodel.DirectionsResponse;
import com.skeleton.util.googledirections.googledirectionmodel.PolylineResponse;
import com.skeleton.util.googlemap.MySupportMapFragment;

import java.util.List;

import io.paperdb.Paper;

import static com.facebook.R.id.add;

/**
 * +++++++++++++++++++++++++++++++
 * +++++++++Click labs +++++++++++
 * +++++++++++++++++++++++++++++++
 */
public class HomeActivity extends BaseActivity implements DrawerMenuFragment.DrawerMenuClickListener, GoogleDirectionsCallback, DirectionPolylinesCallback
        , GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener, LocationFetcherCallBack {
    //mumbai
    private static final double MLAT1 = 19.0760;
    private static final double MLAN1 = 72.8777;
    //shimla
    private static final double MLAT2 = 31.1048;
    private static final double MLAN2 = 77.1734;
    //chandigarh
    private static final double MLAT3 = 30.7333;
    private static final double MLAN3 = 76.7794;
    //Hydrabad
    private static final double MLAT4 = 17.3850;
    private static final double MLAN4 = 78.4867;
    private static final String TAG = "HomeActivity";
    private DrawerLayout drawer;
    private TextView tvTitle;
    private FrameLayout flMain;
    private double latitude, longitude;
    private double lat, log;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private String mLocality;
    private Call placeCall = null;
    private TabLayout tabLayoutHome;
    private Polyline currentPolyline;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        init();
    }

    /**
     * initialization
     */
    private void init() {
        MySupportMapFragment mapFragment = (MySupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(HomeActivity.this);
        getLatLong();
        tvTitle = (TextView) findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolBar(toolbar);
        tvTitle.setText(R.string.tab_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Paper.book().read(SOCIAL_LOGIN_BY, " ").equals(GOOGLE)) {
            setGoogleApiClient();
        }

    }

    /**
     * get lat long amit
     */
    private void getLatLong() {
        LocationFetcher getLocation = new LocationFetcher.Builder(this)
                .setTimeInterval(SET_TIME_INTERVAL)
                .setLocationFetcherCallBack(this)
                .build();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * logout the user
     */
    private void logout() {
        ProgressDialog.showProgressDialog(this);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull final Status status) {
                    Log.d(TAG, "logout status :" + status.isSuccess());
                }
            });
        }
        RestClient.getApiInterface().logout(RetrofitUtils.getHeaderMap(true))
                .enqueue(new ResponseResolver<CommonParams>() {
                    @Override
                    public void success(final CommonParams commonParams) {
                        ProgressDialog.dismissProgressDialog();
                        startActivity(new Intent(HomeActivity.this, TutorialActivity.class));
                    }

                    @Override
                    public void failure(final APIError error) {
                        new CustomAlertDialog.Builder(HomeActivity.this).setMessage(error.getMessage()).show();
                    }
                });
    }

    /**
     * @param fragment fragment
     */
    private void replaceFragment(final Fragment fragment) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fl_home, fragment);
        fragmentTransaction.commit();
    }

    private void setGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(KEY_REQUEST_TOKEN_GOOGLE)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(MyApplication.getAppContext())
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void menuClicked(final int id) {

        switch (id) {
            case R.id.fl_notification:
                break;
            case R.id.fl_schedule:
                break;
            case R.id.fl_add_card:
                startActivity(new Intent(HomeActivity.this, AddCardActivity.class));
                break;
            case R.id.fl_settings:
                break;
            case R.id.fl_tutorials:
                break;
            case R.id.fl_support:
                break;
            case R.id.fl_refer_earn:
//                replaceFragment(ReferAndEarnFragment.newInstance());
                tvTitle.setText(R.string.toolbar_title_refer_and_earn);
                break;
            case R.id.fl_logout:
                logout();
                break;
            default:
                break;
        }
        drawer.closeDrawer(Gravity.START);
    }

    @Override
    public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(@Nullable final Bundle bundle) {
        Toast.makeText(this, "onConnected", Toast.LENGTH_LONG).show();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(3000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            Log.i("latlong", latitude + " " + longitude);
            goToLocationZoom(latitude, longitude, 16);
            MarkerOptions markerOptions = new MarkerOptions().title(getString(R.string.curr_loc))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                    .position(new LatLng(latitude, longitude));
            mGoogleMap.addMarker(markerOptions);

        }
    }

    @Override
    public void onConnectionSuspended(final int i) {

    }

    @Override
    public void onLocationChanged(final Location location) {
        Toast.makeText(this, "onLocationChanged", Toast.LENGTH_LONG).show();
        if (location == null) {
            showAlertDialog(getString(R.string.cant_fetch_location));
        } else {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 16);
            mGoogleMap.moveCamera(update);
            setMarker();
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        LatLng india = new LatLng(lat, log);
//        mGoogleMap.addMarker(new MarkerOptions().position(india)
//                .title("Marker in chandigarh sector 9"));
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        this.mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleApiClientBuilder();
        mGoogleMap.setMyLocationEnabled(true);


        LatLng india = new LatLng(MLAT1, MLAN1);
        googleMap.addMarker(new MarkerOptions()
                .position(india)
                .title(String.valueOf(add))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        india = new LatLng(MLAT2, MLAN2);
        googleMap.addMarker(new MarkerOptions()
                .position(india)
                .title(String.valueOf(add))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(india));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        GoogleDirectionsMap googleDirectionsMap = new GoogleDirectionsMap.Builder(this)
                .setDestinationLatLan(getString(MLAT1, MLAN1))
                .setSourceLatLan(getString(MLAT2, MLAN2))
                .setGoogleDirectionsCallback((GoogleDirectionsCallback) this)
                .setDirectionPolylinesCallback((DirectionPolylinesCallback) this)
                .setWayPoints(getString(MLAT3, MLAN3))
                .setWayPoints(getString(MLAT4, MLAN4))
                .build();
    }

    private String getString(final double lat, final double lng) {
        return lat + "," + lng;
    }


    private void googleApiClientBuilder() {
        mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onSuccess(final Location location) {
        lat = location.getLatitude();
        log = location.getLongitude();

    }

    @Override
    public void onSuccess(final DirectionsResponse response) {

    }

    @Override
    public void onFailure(final String errorMsg) {

    }

    /**
     * @param lat lat;      * @param lng  long;      * @param zoom zoom value;
     */
    public void goToLocationZoom(final double lat, final double lng, final float zoom) {
        LatLng latlong = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlong, zoom);
        mGoogleMap.moveCamera(update);
    }

    /**
     * @param msg of alert dialog
     */
    private void showAlertDialog(final String msg) {
        new CustomAlertDialog.Builder(HomeActivity.this)
                .setMessage(msg).setPositiveButton(R.string.text_ok,
                new CustomAlertDialog.CustomDialogInterface.OnClickListener() {
                    @Override
                    public void onClick() {
                    }
                }).show();
    }

    /**
     * setting up marker;
     */
    public void setMarker() {
        MarkerOptions markerOptions = new MarkerOptions().title(mLocality).icon(BitmapDescriptorFactory.fromResource(R.drawable.location)).position(new LatLng(latitude, longitude));
        mGoogleMap.addMarker(markerOptions);
    }

    @Override
    public void polylineDirections(final PolylineResponse polylineResponse) {
        Log.e("Error", "Test" + polylineResponse.getPolylineDirectionLatLngs().size());
        plotPolyLines(polylineResponse.getPolylineDirectionLatLngs(), 16, String.valueOf(R.color.colorAccent));

    }

    /**
     * @param latLngs       latlngs
     * @param polylineWidth width
     * @param colorPoly     color
     */
    private void plotPolyLines(final List<LatLng> latLngs, final double polylineWidth, final String colorPoly) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        Log.e("Error", colorPoly);
        currentPolyline = mGoogleMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .width((float) polylineWidth)
                .color(Integer.parseInt(colorPoly)));
        currentPolyline.setVisible(true);

    }
}
