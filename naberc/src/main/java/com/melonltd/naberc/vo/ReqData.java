package com.melonltd.naberc.vo;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.List;

public class ReqData {
    public String uuid = "";
    public List<String> uuids = Lists.<String>newArrayList();
    public String search_type = "";
    public String area = "";
    public int page = 0;
    public int top = 0;
    public String longitude = "0.0";
    public String latitude = "0.0";
    public String category = "";
    public String json_data = "";
    public String date = "";
    public String message = "";
    public String type = "";
    public String name = "";
    public String status = "";
    public boolean loadingMore = true;

//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
//
//    public List<String> getUuids() {
//        return uuids;
//    }
//
//    public void setUuids(List<String> uuids) {
//        this.uuids = uuids;
//    }
//
//    public String getSearch_type() {
//        return search_type;
//    }
//
//    public void setSearch_type(String search_type) {
//        this.search_type = search_type;
//    }
//
//    public String getArea() {
//        return area;
//    }
//
//    public void setArea(String area) {
//        this.area = area;
//    }
//
//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public int getTop() {
//        return top;
//    }
//
//    public void setTop(int top) {
//        this.top = top;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getJson_data() {
//        return json_data;
//    }
//
//    public void setJson_data(String json_data) {
//        this.json_data = json_data;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("uuid", uuid)
                .add("uuids", uuids)
                .add("search_type", search_type)
                .add("area", area)
                .add("page", page)
                .add("top", top)
                .add("longitude", longitude)
                .add("latitude", latitude)
                .add("category", category)
                .add("json_data", json_data)
                .add("date", date)
                .add("message", message)
                .add("type", type)
                .add("name", name)
                .add("status", status)
                .toString();
    }
}
