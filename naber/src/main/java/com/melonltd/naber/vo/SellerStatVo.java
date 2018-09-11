package com.melonltd.naber.vo;

import com.google.common.base.MoreObjects;

public class SellerStatVo {
    public String year_income;
    public String month_income;
    public String day_income;
    public String finish_count;
    public String[] status_dates;
    public String unfinish_count;
    public String processing_count;
    public String can_fetch_count;
    public String cancel_count;

    public String getYear_income() {
        return year_income;
    }

    public void setYear_income(String year_income) {
        this.year_income = year_income;
    }

    public String getMonth_income() {
        return month_income;
    }

    public void setMonth_income(String month_income) {
        this.month_income = month_income;
    }

    public String getDay_income() {
        return day_income;
    }

    public void setDay_income(String day_income) {
        this.day_income = day_income;
    }

    public String getFinish_count() {
        return finish_count;
    }

    public void setFinish_count(String finish_count) {
        this.finish_count = finish_count;
    }

    public String[] getStatus_dates() {
        return status_dates;
    }

    public void setStatus_dates(String[] status_dates) {
        this.status_dates = status_dates;
    }

    public String getUnfinish_count() {
        return unfinish_count;
    }

    public void setUnfinish_count(String unfinish_count) {
        this.unfinish_count = unfinish_count;
    }

    public String getProcessing_count() {
        return processing_count;
    }

    public void setProcessing_count(String processing_count) {
        this.processing_count = processing_count;
    }

    public String getCan_fetch_count() {
        return can_fetch_count;
    }

    public void setCan_fetch_count(String can_fetch_count) {
        this.can_fetch_count = can_fetch_count;
    }

    public String getCancel_count() {
        return cancel_count;
    }

    public void setCancel_count(String cancel_count) {
        this.cancel_count = cancel_count;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("year_income",year_income)
                .add("month_income",month_income)
                .add("day_income",day_income)
                .add("finish_count",finish_count)
                .add("status_dates",status_dates)
                .add("unfinish_count",unfinish_count)
                .add("processing_count",processing_count)
                .add("can_fetch_count",can_fetch_count)
                .add("cancel_count",cancel_count)
                .toString();
    }
}
