package com.melonltd.naberc.util;

import android.location.Location;

public class DistanceTools {

    public static double getGoogleDistance(Location start, Location end) {
        double lat1 = (Math.PI / 180) * start.getLatitude();
        double lat2 = (Math.PI / 180) * end.getLatitude();
        double lon1 = (Math.PI / 180) * start.getLongitude();
        double lon2 = (Math.PI / 180) * end.getLongitude();

        double R = 6371;//地球半径
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.cos(lon2 - lon1))  * R;
        return d * 1000;
    }
}
