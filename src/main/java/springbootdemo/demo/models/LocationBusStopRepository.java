package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface LocationBusStopRepository extends CrudRepository<LocationBusStop, Long> {

    ArrayList<LocationBusStop> findByLocationId(Integer locationId);

}
