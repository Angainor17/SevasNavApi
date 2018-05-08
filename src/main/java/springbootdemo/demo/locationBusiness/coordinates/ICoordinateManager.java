package springbootdemo.demo.locationBusiness.coordinates;

import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;

import java.util.ArrayList;

public interface ICoordinateManager {

    ArrayList<BusStop> busStopInRadius(Location location);

    ArrayList<BusStop> busStopInRadius(UncheckedUserLocation location);

}
