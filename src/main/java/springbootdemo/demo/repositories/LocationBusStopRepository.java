package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.LocationBusStop;

import java.util.ArrayList;

public interface LocationBusStopRepository extends CrudRepository<LocationBusStop, Long> {

    ArrayList<LocationBusStop> findByLocationId(Integer locationId);

    ArrayList<LocationBusStop> findByLocationIdAndBusStopIds(Integer locationId, Integer busStopId);

}
