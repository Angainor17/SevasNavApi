package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @Column(name = "id")
    @JsonSerialize
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @JsonSerialize
    private String name;

    @Column(name = "transport_type")
    @JsonSerialize
    private String transportType;

    @Column(name = "start_bus_stop_name")
    @JsonSerialize
    private String lastBusStopName;

    @Column(name = "end_bus_stop_name")
    @JsonSerialize
    private String startBusStopName;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "route_bus_stop",
            joinColumns = {@JoinColumn(name = "route_id")},
            inverseJoinColumns = {@JoinColumn(name = "bus_stop_id")}
    )
    private Set<BusStop> busStops;

    public Set<BusStop> getBusStops() {
        return busStops;
    }

    public String getTransportType() {
        return transportType;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
