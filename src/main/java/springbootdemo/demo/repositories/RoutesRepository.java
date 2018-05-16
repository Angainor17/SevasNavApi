package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.Route;

import java.util.List;

public interface RoutesRepository extends CrudRepository<Route, Long> {

    List<Route> findById(int id);

    Route findByName(String name);

}
