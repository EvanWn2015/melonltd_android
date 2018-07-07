package com.melonltd.naber.util;

import android.location.Location;

import com.melonltd.naber.vo.LocationVo;


public class DistanceTools {

    public static double getDistance(Location start, LocationVo end) {
        if (start == null){
            return 0.0;
        }
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.latitude;
        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.longitude;

        double R = 6371;//地球半径
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))  * R;
        return d;
    }

//    public static String getGoogleDistance(Location start, LocationVo end) {
//        if (start == null || end == null){
//            return "公里";
//        }
//        double lat1 = (Math.PI / 180) * start.getLatitude();
//        double lat2 = (Math.PI / 180) * end.latitude;
//        double lon1 = (Math.PI / 180) * start.getLongitude();
//        double lon2 = (Math.PI / 180) * end.longitude;
//
//        double R = 6371;//地球半径
//        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))  * R;
//
//        if (d < 0) {
//            return "";
//        }
//
//        String result = Tools.FORMAT.decimal("0.0", d);
//        return (result.equals("0.0") ? "0.1" : result) + "公里";
//    }

//    private static final double EARTH_RADIUS = 6378137.0;
//
//    public static double gps2m(Location start, LocationVo end) {
//        double radLat1 = (start.getLatitude() * Math.PI / 180.0);
//        double radLat2 = (end.latitude * Math.PI / 180.0);
//        double a = radLat1 - radLat2;
//        double b = (start.getLatitude() - end.longitude) * Math.PI / 180.0;
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        return s;
//    }
}
