package springbootdemo.demo.models;

import javax.persistence.*;


@Entity
@Table(name = "location_bus_stop")
public class LocationBusStop {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "bus_stop_ids")
    private Integer busStopIds;

    @Column(name = "position")
    private Integer position;

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setBusStop(Integer busStop) {
        this.busStopIds = busStop;
    }

    public Integer getBusStopID() {
        return busStopIds;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
