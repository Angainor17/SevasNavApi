package springbootdemo.demo.webSockets;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class LocationApi {

    @JsonSerialize
    private String longitude;

    @JsonSerialize
    private String latitude;

    @JsonSerialize
    private String userId;

    @JsonSerialize
    private ArrayList<Route> routes;

    public LocationApi(Location location) {
        this.latitude = location.getLatitudeString();
        this.longitude = location.getLongitudeString();
        this.userId = location.getUserId();

        Set<Route> routeSet = location.getRoutes();
        if (routeSet == null) {
            routeSet = new HashSet<>();
        }

        this.routes = new ArrayList<>(routeSet);
    }
}
