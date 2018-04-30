package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteBusStopRepository extends CrudRepository<RouteBusStops, Long> {

    List<RouteBusStops> findRouteBusStopsByRouteId(Integer routeId);
}
