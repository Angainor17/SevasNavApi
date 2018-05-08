package springbootdemo.demo.locationBusiness.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Calendar;

public class UncheckedUserLocation {

    @JsonSerialize
    private String longitude;

    @JsonSerialize
    private String latitude;

    @JsonSerialize
    private String userId;

    @JsonSerialize
    private String time;

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getUserId() {
        return userId;
    }

    public Calendar getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        return calendar;
    }
}
