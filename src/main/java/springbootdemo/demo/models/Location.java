package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
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

    @Column(name = "time")
    @JsonSerialize
    private String time;

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

}
