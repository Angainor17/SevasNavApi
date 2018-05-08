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

    public Double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public Double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public String getLongitudeString() {
        return longitude;
    }

    public String getLatitudeString() {
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
