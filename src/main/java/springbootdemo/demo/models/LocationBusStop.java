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
    private Integer busStop;

    @Column(name = "position")
    private Integer position;

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setBusStop(Integer busStop) {
        this.busStop = busStop;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
