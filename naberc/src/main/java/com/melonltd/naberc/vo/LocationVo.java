package com.melonltd.naberc.vo;

public class LocationVo {
    public double latitude;
    public double longitude;

    public static LocationVo of (double latitude, double longitude){
        LocationVo vo = new LocationVo();
        vo.latitude = latitude;
        vo.longitude = longitude;
        return  vo;
    }
}
