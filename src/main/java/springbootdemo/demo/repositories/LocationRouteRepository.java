package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.LocationRoute;

public interface LocationRouteRepository extends CrudRepository<LocationRoute, Long> {

    LocationRoute findByLocationIdAndRouteId(Integer locationId, Integer routeId);

}
