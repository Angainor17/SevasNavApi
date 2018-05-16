package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Direction;

public interface DirectionRepository extends CrudRepository<Direction, Long> {

}
