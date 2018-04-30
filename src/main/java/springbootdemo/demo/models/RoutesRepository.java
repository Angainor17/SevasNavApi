package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoutesRepository extends CrudRepository<Route, Long> {

    List<Route> findById(int id);

}
