package springbootdemo.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "location_route")
public class LocationRoute {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "locations_id")
    private Integer locationId;

    @Column(name = "routes_id")
    private Integer routeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }
}
