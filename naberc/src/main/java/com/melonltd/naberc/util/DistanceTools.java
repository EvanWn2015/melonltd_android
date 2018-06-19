package com.melonltd.naberc.util;

import android.location.Location;
import android.util.Log;

import com.google.common.primitives.Doubles;
import com.melonltd.naberc.vo.LocationVo;

import java.text.DecimalFormat;


public class DistanceTools {
    private static final String TAG = DistanceTools.class.getSimpleName();

    public static double getDistance(Location start, LocationVo end) {
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.latitude;
        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.longitude;

        double R = 6371;//地球半径
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon2 - lon1))  * R;
        return d * 1000;
    }

    public static String getGoogleDistance(Location start, LocationVo end) {
        if (start == null || end == null){
            return "公里";
        }
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.latitude;
        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.longitude;

        double R = 6371;//地球半径
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))  * R;

        if (d < 0) {
            return "";
        }

        String result = Tools.FORMAT.decimal("0.0", d);
        return (result.equals("0.0") ? "0.1" : result) + "公里";
    }
}
