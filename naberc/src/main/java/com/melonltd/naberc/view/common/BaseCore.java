package com.melonltd.naberc.view.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.melonltd.naberc.R;
import com.melonltd.naberc.model.api.ApiCallback;
import com.melonltd.naberc.model.api.ApiManager;
import com.melonltd.naberc.model.api.ThreadCallback;
import com.melonltd.naberc.model.bean.Model;
import com.melonltd.naberc.model.service.SPService;
import com.melonltd.naberc.util.DistanceTools;
import com.melonltd.naberc.util.Tools;
import com.melonltd.naberc.view.factory.PageType;
import com.melonltd.naberc.vo.LocationVo;
import com.melonltd.naberc.vo.RestaurantTemplate;

import java.util.Arrays;
import java.util.List;


public abstract class BaseCore extends AppCompatActivity implements LocationListener {
    private static final String TAG = BaseCore.class.getSimpleName();
    public static Context context;
    private static ConnectivityManager cm;
    private LocationManager locationManager;

    public static String FRAGMENT_TAG = PageType.LOGIN.name();

    public static final int CAMERA_CODE = 8765;
    public static final String[] CAMERA = new String[]{Manifest.permission.CAMERA};

    public static final int LOCATION_CODE = 9876;
    public static final String[] LOCATION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final int IO_STREAM_CODE = 1987;
    public static final String[] IO_STREAM = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    public static FirebaseUser currentUser ;

    public static boolean IS_USER = true;
    public static boolean IS_HAS_ACC = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        String toekn = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, toekn + "");
        getCurrentUser(this);


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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
        SPService.getInstance(getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE));
    }

    public static void getCurrentUser (Activity activity){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInAnonymously()
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentUser = auth.getCurrentUser();
                            Log.d(TAG, "signInAnonymously:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                        }
                    }
                });
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
            Tools.GoogleVersion.checkVersion(context, this);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        Log.d(TAG, grantResults.length +"" );


//        switch (requestCode) {
////            case CAMERA_CODE:
////                break;
//            case LOCATION_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    setLocationListener();
//                }else{
//                    return;
//                }
//                break;
//            case IO_STREAM_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    setLocationListener();
//                }else{
//                    return;
//                }
//                break;
//            default:
//
//        }
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Model.LOCATION = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onLocationChanged(Location loc) {
        if (loc != null) {
            Model.LOCATION = loc;
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


    public static void loadRestaurantTemplate (Context context){
        Model.RESTAURANT_TEMPLATE.clear();
        ApiManager.restaurantTemplate(new ApiCallback(context) {
            @Override
            public void onSuccess(String responseBody) {
                Log.d(TAG, responseBody);
                List<RestaurantTemplate> list = Tools.JSONPARSE.fromJsonList(responseBody, RestaurantTemplate[].class);
                for (int i=0; i<list.size(); i++){
                   list.get(i).distance =  DistanceTools.getDistance(Model.LOCATION, LocationVo.of(list.get(i).latitude, list.get(i).longitude));
                }
                Ordering<RestaurantTemplate> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<RestaurantTemplate, Double>() {
                    public Double apply(RestaurantTemplate template) {
                        return template.distance;
                    }
                });
                Model.RESTAURANT_TEMPLATE.addAll(Lists.partition(ordering.sortedCopy(list), 10));
                Log.d(TAG, Model.RESTAURANT_TEMPLATE.toString());
            }

            @Override
            public void onFail(Exception error, String msg) {

            }
        });
    }

//    public void removeFragment(){
//        LoginFragment.FRAGMENT = null;
//        RecoverPasswordFragment.FRAGMENT = null;
//        RegisteredFragment.FRAGMENT = null;
//        VerifySMSFragment.FRAGMENT = null;
//        RegisteredSellerFragment.FRAGMENT = null;
//        HomeFragment.FRAGMENT = null;
//        RestaurantFragment.FRAGMENT = null;
//        RestaurantDetailFragment.FRAGMENT = null;
//        CategoryMenuFragment.FRAGMENT = null;
//        MenuDetailFragment.FRAGMENT = null;
//        ShoppingCartFragment.FRAGMENT = null;
//        SubmitOrdersFragment.FRAGMENT = null;
//        HistoryFragment.FRAGMENT = null;
//        OrderDetailFragment.FRAGMENT = null;
//        SetUpFragment.FRAGMENT = null;
//        AccountDetailFragment.FRAGMENT = null;
//        SimpleInformationFragment.FRAGMENT = null;
//        ResetPasswordFragment.FRAGMENT = null;
//        SellerSearchFragment.FRAGMENT = null;
//        SellerOrdersFragment.FRAGMENT = null;
//        SellerStatFragment.FRAGMENT = null;
//        SellerOrdersLogsFragment.FRAGMENT = null;
//        SellerOrderLogsDetailFragment.FRAGMENT = null;
//        SellerRestaurantFragment.FRAGMENT = null;
//        SellerCategoryListFragment.FRAGMENT = null;
//        SellerMenuEditFragment.FRAGMENT = null;
//        SellerSetUpFragment.FRAGMENT = null;
//        SellerDetailFragment.FRAGMENT = null;
//        SellerSimpleInformationFragment.FRAGMENT = null;

//        for (Fragment fragment : fragmentManager.getFragments()) {
//            Log.d(TAG, fragment + "");
//            if (fragment instanceof AbsPageFragment) {
//                fragmentManager.beginTransaction().remove(fragment).commit();
//            }
//        }
//    }

}
