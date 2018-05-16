package springbootdemo.demo.locationBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.coordinates.ICoordinateManager;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.locationBusiness.managers.BusStopManager;
import springbootdemo.demo.locationBusiness.managers.LocationManager;
import springbootdemo.demo.locationBusiness.managers.RouteManager;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

@Service
public class LocationProxy {

    @Autowired
    private IDataProvider dataProvider;

    @Autowired
    private ICoordinateManager coordinateManager;

    @Autowired
    private BusStopManager busStopManager;

    @Autowired
    private RouteManager routeManager;

    @Autowired
    private LocationManager locationManager;

    public void addNewLocation(UncheckedUserLocation uncheckedUserLocation) {
        final String userId = uncheckedUserLocation.getUserId();
        ArrayList<BusStop> busStops = coordinateManager.busStopInRadius(uncheckedUserLocation);

        Location location;
        if (!dataProvider.hasLocationWithUserId(userId)) {
            location = locationManager.initNewLocation(uncheckedUserLocation);

        } else {
            location = dataProvider.updateLocation(uncheckedUserLocation);
        }

        if(busStops.isEmpty()){
            return;
        }

        busStopManager.addNewBusStop(location, busStops);

        busStopManager.removeUselessBusStops(location);//must be first
        routeManager.removeUselessRoutes(location);

//        locationManager.checkRouteVisibility(location);
//        locationManager.removeUselessLocation(location);
    }

    public ArrayList<Route> getRoutesList(String userId) {
        ArrayList<Route> routes = dataProvider.getRoutes(userId);
        if (routes != null) {
            return routes;
        }
        return new ArrayList<>();
    }
}
