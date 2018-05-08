package springbootdemo.demo.locationBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.coordinates.ICoordinateManager;
import springbootdemo.demo.locationBusiness.data.DataProvider;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

@Service
public class UsersLocationManager {

    @Autowired
    private IDataProvider dataProvider;

    @Autowired
    private ICoordinateManager coordinateManager;

    public void addNewLocation(UncheckedUserLocation uncheckedUserLocation) {
        ArrayList<BusStop> busStops = coordinateManager.busStopInRadius(uncheckedUserLocation);

        if (!busStops.isEmpty()) {

            if (!dataProvider.hasLocationWithUserId(uncheckedUserLocation.getUserId())) {
                initNewLocation(uncheckedUserLocation);
            }
        }
    }

    private void initNewLocation(UncheckedUserLocation uncheckedUserLocation) {
        Location newLocation = new Location(uncheckedUserLocation);
        dataProvider.addLocation(newLocation);

        for (Route route : dataProvider.getAllRoutes()) {
            dataProvider.addRoute(newLocation.getUserId(), route);
        }
    }
}
