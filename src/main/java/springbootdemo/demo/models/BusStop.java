package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import springbootdemo.demo.locationBusiness.coordinates.DistanceCalculator;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;

import javax.persistence.*;

@Entity
@Table(name = "bus_stop")
public class BusStop {

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

    @Column(name = "name")
    @JsonSerialize
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public Double getLatitude() {
        return Double.parseDouble(latitude);
    }


    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public double distanceTo(Location location) {
        return DistanceCalculator.distance(
                getLatitude(),
                getLongitude(),
                location.getLatitude(),
                location.getLongitude()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BusStop) {
            BusStop newBusStop = (BusStop) obj;
            boolean equalName = getName().equals(newBusStop.getName());
            boolean equalLatitude = latitude.equals(newBusStop.latitude);
            boolean equalLongitude = longitude.equals(newBusStop.longitude);

            return equalName && equalLatitude && equalLongitude;
        }
        return false;
    }

    public double distanceTo(UncheckedUserLocation location) {
        return DistanceCalculator.distance(
                getLatitude(),
                getLongitude(),
                location.getLatitude(),
                location.getLongitude()
        );
    }
}
