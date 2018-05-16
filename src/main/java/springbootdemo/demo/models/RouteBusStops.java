package springbootdemo.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "route_bus_stop")
public class RouteBusStops {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "position")
    private Integer position;

    @JsonIgnore
    @Column(name = "bus_stop_id")
    private Integer busStopId;

    @JsonSerialize(as = BusStop.class)
    @OneToOne(targetEntity = BusStop.class)
    @MapsId(value = "bus_stop_id")
    private BusStop busStop;

    @JsonIgnore
    @Column(name = "route_id")
    private Integer routeId;

    public Integer getId() {
        return id;
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getBusStopId() {
        return busStopId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public BusStop getBusStop() {
        return busStop;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setBusStopId(Integer busStopId) {
        this.busStopId = busStopId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }
}
