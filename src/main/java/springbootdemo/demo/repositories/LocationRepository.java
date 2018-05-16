package springbootdemo.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springbootdemo.demo.models.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByUserId(String userId);

}
