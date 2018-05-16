package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@Entity
@Table(name = "route_direction")
public class RouteDirection {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonSerialize
    private Integer id;

    @Column(name = "direction_id")
    @JsonSerialize
    private Integer directionId;

    @Column(name = "rout_id")
    @JsonSerialize
    private Integer routeId;

    @Column(name = "position")
    @JsonSerialize
    private Integer position;

    @JsonSerialize(as = Direction.class)
    @OneToOne(targetEntity = Direction.class)
    @MapsId(value = "direction_id")
    private Direction direction;

    public RouteDirection() {

    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirectionId(Integer directionId) {
        this.directionId = directionId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }
}
