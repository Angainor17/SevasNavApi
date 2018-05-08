package springbootdemo.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "transport_type")
    private String transportType;

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
