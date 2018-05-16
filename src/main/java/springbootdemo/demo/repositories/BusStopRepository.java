package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import springbootdemo.demo.models.BusStop;

public interface BusStopRepository extends CrudRepository<BusStop, Long> {

    BusStop findById(Integer id);

}
