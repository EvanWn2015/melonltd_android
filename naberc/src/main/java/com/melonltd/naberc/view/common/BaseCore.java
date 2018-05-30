package com.melonltd.naberc.view.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.preferences.SharedPreferencesService;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.common.type.PageType;

public abstract class BaseCore extends AppCompatActivity implements LocationListener {
    private static final String TAG = BaseCore.class.getSimpleName();
    public static Context context;
    private static ConnectivityManager cm;
    private LocationManager locationManager;

    public static String FRAGMENT_TAG = PageType.LOGIN.name();

    public static final int CAMERA_CODE = 8765;
    public static final String[] CAMERA = new String[]{Manifest.permission.CAMERA};

    private static final int LOCATION_CODE = 9876;
    public static final String[] LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final int IO_STREAM_CODE = 1987;
    public static final String[] IO_STREAM = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

//    public static FirebaseAuth auth = FirebaseAuth.getInstance();
//    public static FirebaseUser currentUser = auth.getCurrentUser();

    public FragmentManager fragmentManager = getSupportFragmentManager();
    //    public static PopUpDialog POPUP = PopUpDialog.getInstance();
    public static boolean IS_USER = true;
    public static boolean IS_HAS_ACC = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        String toekn = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, toekn + "");
//        try {
//            int v = getPackageManager().getPackageInfo("com.google.android.gms", 0 ).versionCode;
//            Log.d(TAG, v +"");
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        GoogleApiAvailability.isGooglePlayServicesAvailable(context);
//


//         int version = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
//        Log.d(TAG, "google play service version == >" + version);

        // TODO do get GCM token
//        Intent intent = new Intent(BasisActivity.this, RegistrationIntentService.class);
//        startService(intent);

        // TODO init PopUp
//        POPUP.setTransaction(fragmentManager);
        // TODO init Manager
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        // TODO 權限相關
        // 相機權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, CAMERA, CAMERA_CODE);
        }

        // TODO GPS 權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, LOCATION, LOCATION_CODE);
        } else {
            setLocationListener();
        }

        // TODO 寫入權限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, IO_STREAM, IO_STREAM_CODE);
        }

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
//        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        Log.d(TAG, "Location : " + loc);

        // TODO  init SharedPreferences
        SharedPreferencesService.newInstance(getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO check mobile has net work
        if (!Tools.NETWORK.hasNetWork(cm)) {
            // TODO show Dialog
            Log.d(TAG, "no NETWORK? ???");
        } else {
            // TODO check google service version
            // Tools.GoogleVersion.checkVersion(context, this);
        }

//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(this);
//        if (result != ConnectionResult.SUCCESS) {
//            if (googleAPI.isUserResolvableError(result)) {
//                Dialog dialog = googleAPI.getErrorDialog(this, result, 9000);
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                    @Override
//                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                        if (i == KeyEvent.KEYCODE_BACK) {
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//                dialog.show();
//            }
//        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        switch (requestCode) {
//            case CAMERA_CODE:
//                break;
            case LOCATION_CODE:
                setLocationListener();
                break;
            case IO_STREAM_CODE:
                break;
            default:

        }
    }

    // TODO 偏好儲存設定 寫入
    public SharedPreferences getPreferencesEditor() {
        return getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
    }

    // TODO 偏好儲存設定 讀取
    public SharedPreferences getRead() {
        return getPreferences(Context.MODE_PRIVATE);
    }

    @SuppressLint("MissingPermission")
    private void setLocationListener() {
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
//            Log.d(TAG, "Latitude: " + loc.getLatitude());
//            Log.d(TAG, "Longitude: " + loc.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "onStatusChanged: ");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG, "onProviderEnabled: ");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG, "onProviderDisabled: ");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
