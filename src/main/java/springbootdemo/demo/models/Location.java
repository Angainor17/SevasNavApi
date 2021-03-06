package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_locations")
public class Location {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize
    private Integer id;

    @Column(name = "longitude")
    @JsonSerialize
    private String longitude;

    @Column(name = "latitude")
    @JsonSerialize
    private String latitude;

    @Column(name = "uid")
    @JsonSerialize
    private String userId;

    @Column(name = "first_time")
    @JsonSerialize
    private String firstTime;

    @Column(name = "time")
    @JsonSerialize
    private String time;

    @Column(name = "is_visible")
    @JsonSerialize
    private Boolean isVisisble;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "location_bus_stop",
            joinColumns = {@JoinColumn(name = "location_id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_stop_ids")}
    )
    @JsonSerialize
    private Set<BusStop> busStops;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "location_route",
            joinColumns = {@JoinColumn(name = "locations_id")},
            inverseJoinColumns = {@JoinColumn(name = "routes_id")}
    )
    @JsonSerialize
    private Set<Route> routes;

    public Location() {
    }

    public Location(UncheckedUserLocation userLocation) {
        this.latitude = userLocation.getLatitudeString();
        this.longitude = userLocation.getLongitudeString();
        this.time = "" + userLocation.getTime().getTimeInMillis();
        this.userId = userLocation.getUserId();
    }

    public long getTime() {
        return Long.parseLong(time);
    }

    public String getFirstTimeString() {
        return firstTime;
    }

    public long getFirstTime() {
        return Long.parseLong(firstTime);
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public Boolean getVisisble() {
        return isVisisble;
    }

    public void setVisible(Boolean isVisible) {
        this.isVisisble = isVisible;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Set<BusStop> getBusStops() {
        return busStops;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public String getLongitudeString() {
        return (longitude);
    }

    public String getLatitudeString() {
        return (latitude);
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public void update(UncheckedUserLocation uncheckedUserLocation) {
        this.longitude = uncheckedUserLocation.getLongitudeString();
        this.latitude = uncheckedUserLocation.getLatitudeString();
        this.time = "" + uncheckedUserLocation.getTime().getTimeInMillis();
    }
}
