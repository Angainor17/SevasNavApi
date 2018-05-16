package springbootdemo.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;


@Entity
@Table(name = "directions")
public class Direction {

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

    public Direction() {

    }

    public Direction(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }
}
