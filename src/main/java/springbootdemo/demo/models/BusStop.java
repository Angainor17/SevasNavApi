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

    private Double getLongitude() {
        return Double.parseDouble(longitude);
    }

    private Double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public double distanceTo(Location location) {
        return DistanceCalculator.distance(
                getLatitude(),
                getLongitude(),
                location.getLatitude(),
                location.getLongitude()
        );
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
