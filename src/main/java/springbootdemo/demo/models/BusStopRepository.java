package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;

public interface BusStopRepository extends CrudRepository<BusStop, Long> {

    BusStop findById(Integer id);
}
