package com.melonltd.naberc.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.common.base.Strings;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by evan on 2018/1/5.
 */

public class Tools {
    public static final String TAG = Tools.class.getSimpleName();
    public static Parse PARSE = new Parse();
    public static Format FORMAT = new Format();
    //    public static GCM gcm = new GCM();
    public static Network NETWORK = new Network();
    public static Longitude LONGITUDE = new Longitude();
//    public static Bitmaps BITMAPS = new Bitmaps();
    public static Gson GSON = new Gson();
    public static MakeUp MAKEUP = new MakeUp();

    public static class Format {
        private static DecimalFormat decimal = new DecimalFormat();
        private static SimpleDateFormat simpleDate = new SimpleDateFormat();

        public static String decimal(String pattern, Number value) {
            decimal.applyPattern(pattern);
            return decimal.format(value);
        }

        public static String date(Locale locale, String template, Long time) {
            simpleDate = new SimpleDateFormat(template, locale);
            return simpleDate.format(time);
        }


        public static String toUTCDateTime(Date date){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:SS.sss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return format.format(date);
        }
    }

    public static class Parse {
        private static Gson GSON = new Gson();

        public final static String toJson(Object obj) {
            return GSON.toJson(obj);
        }

        public final static <T> T fromJson(String json, Class<T> classOfT) {
            GSON.fromJson(json, (Type) classOfT);
            return GSON.fromJson(json, (Type) classOfT);
        }
    }

//    public static class GCM {
//        private static final String SenderID = "361595324240";
//
//        public static void deleteToken(final Context context) throws RuntimeException {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        InstanceID.getInstance(context).deleteToken(SenderID, GoogleCloudMessaging.INSTANCE_ID_SCOPE);
//                    } catch (IOException e) {
//                        Log.e(TAG, "delete GCM token fail !!", e);
//                        throw new RuntimeException("delete GCM token fail !!", e);
//                    }
//                }
//            }).start();
//        }
//
//        public static void getToken(final Context context) throws RuntimeException {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        String token = InstanceID.getInstance(context).getToken(SenderID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//                        Log.d(TAG, token);
//                    } catch (IOException e) {
//                        Log.e(TAG, "get GCM token fail !!", e);
//                        throw new RuntimeException("get GCM token fail !!", e);
//                    }
//                }
//            }).start();
//        }
//    }

    public static class Network {
        public static boolean hasNetWork(ConnectivityManager cm) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                int networkType = activeNetwork.getType();
                return networkType == ConnectivityManager.TYPE_WIFI || networkType == ConnectivityManager.TYPE_MOBILE;
            } else {
                return false;
            }
        }
    }

    public static class Longitude {

        @SuppressLint("MissingPermission")
        public static Location get(Context context) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc == null) {
                Log.d(TAG, "GPS fail");
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (loc == null) {
                Log.d(TAG, "NETWORK fail");
            }
            return loc;
        }
    }

    public static class GoogleVersion {
        public static boolean checkVersion(Context context, Activity activity) {
            GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
            int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
            if (googleAPI.isUserResolvableError(result)) {
                Dialog dialog = googleAPI.getErrorDialog(activity, result, 9000);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        return i == KeyEvent.KEYCODE_BACK;
                    }
                });
                dialog.show();
            }
            return false;
        }
    }


    public static class MakeUp {
        public enum Direction {RIGHT, LEFT}

        public static String makeUpCharacter(String src, int min, Direction direction) {
            String result = "";
            if (Strings.isNullOrEmpty(src)) {
                for (int i = 0; i < min; i++) {
//                    result = result + "\u3000";
                    result = result + "\u0020";
                }
            } else if (src.toCharArray().length - min < 0) {
                for (int i = 0; i < min - src.toCharArray().length; i++) {
                    result = result + "\u0020";
//                    result = result + "\u3000";
                }
                result = direction.equals(Direction.RIGHT) ? src + result : result + src;
            } else {
                result = src;
            }
            return result;
        }
    }
}
