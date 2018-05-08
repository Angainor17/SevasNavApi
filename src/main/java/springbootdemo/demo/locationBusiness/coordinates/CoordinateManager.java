package springbootdemo.demo.locationBusiness.coordinates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;

import java.util.ArrayList;

@Service
public class CoordinateManager implements ICoordinateManager {

    private final float MATCH_RADIUS = 100f;

    @Autowired
    IDataProvider dataProvider;

    @Override
    public ArrayList<BusStop> busStopInRadius(Location location) {
        ArrayList<BusStop> busStopsInRadius = new ArrayList<>();

        for (BusStop busStop : dataProvider.getAllBusStop()) {
            if (busStop.distanceTo(location) < MATCH_RADIUS) {
                busStopsInRadius.add(busStop);
            }
        }

        return busStopsInRadius;
    }

    @Override
    public ArrayList<BusStop> busStopInRadius(UncheckedUserLocation location) {
        ArrayList<BusStop> busStopsInRadius = new ArrayList<>();

        for (BusStop busStop : dataProvider.getAllBusStop()) {
            if (busStop.distanceTo(location) < MATCH_RADIUS) {
                busStopsInRadius.add(busStop);
            }
        }
        return busStopsInRadius;
    }
}
