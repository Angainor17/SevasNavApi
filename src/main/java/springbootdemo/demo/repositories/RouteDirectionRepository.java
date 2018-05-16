package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.RouteDirection;

import java.util.ArrayList;

public interface RouteDirectionRepository extends CrudRepository<RouteDirection, Long> {

    ArrayList<RouteDirection> findRouteDirectionByRouteId(Integer routeId);

}
