package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.RouteBusStops;

import java.util.ArrayList;
import java.util.List;

public interface RouteBusStopRepository extends CrudRepository<RouteBusStops, Long> {

    ArrayList<RouteBusStops> findRouteBusStopsByRouteId(Integer routeId);
}
