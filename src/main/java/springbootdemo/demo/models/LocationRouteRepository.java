package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;

public interface LocationRouteRepository extends CrudRepository<LocationRoute, Long> {

    LocationRoute findByLocationIdAndRouteId(Integer locationId, Integer routeId);

}
