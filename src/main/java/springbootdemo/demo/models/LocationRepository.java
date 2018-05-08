package springbootdemo.demo.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByUserId(String userId);

}
