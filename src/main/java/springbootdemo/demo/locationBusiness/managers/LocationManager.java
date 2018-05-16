package springbootdemo.demo.locationBusiness.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;
import java.util.Calendar;

@Service
public class LocationManager {

    @Autowired
    private IDataProvider dataProvider;

    public Location initNewLocation(UncheckedUserLocation uncheckedUserLocation) {
        Location newLocation = new Location(uncheckedUserLocation);
        dataProvider.addLocation(newLocation);

        initRoutesList(newLocation);
        return dataProvider.getLocation(newLocation.getUserId());
    }

    public void checkRouteVisibility(Location location) {
        checkRoutesAndBusStopsCount(location);

        checkWaitForBus();//TODO
        checkOutOfBus();//TODO
        checkAwayFromRoute();//TODO
    }

    private void checkAwayFromRoute() {
        //TODO
    }

    private void checkOutOfBus() {
        //TODO
    }

    private void checkWaitForBus() {
        //TODO
    }

    public void removeUselessLocation(Location location) {
        ArrayList<Route> routes = dataProvider.getRoutes(location.getUserId());
        int routesCount = 0;
        if (routes != null) {
            routesCount = routes.size();
        }

        if (routesCount == 0 && isTooOld(location)) {
//            dataProvider.removeLocation(location.getUserId());
        }
    }

    private boolean isTooOld(Location location) {
        return location.getFirstTime() < Calendar.getInstance().getTimeInMillis() - 10 * 60 * 1000L;
    }

    private void checkRoutesAndBusStopsCount(Location location) {
        int busStopsCount = getBusStopCount(location);
        int routesCount = getRoutesCount(location);

        if (busStopsCount > 0 && routesCount > 0) {
            location.setVisible(true);
            dataProvider.updateLocation(location);
            return;
        }
        location.setVisible(false);
    }

    private int getBusStopCount(Location location) {
        ArrayList<BusStop> busStops = dataProvider.getBusStopList(location.getUserId());
        if (busStops != null) {
            return busStops.size();
        }
        return 0;
    }

    private int getRoutesCount(Location location) {
        ArrayList<Route> routes = dataProvider.getRoutes(location.getUserId());
        if (routes != null) {
            return routes.size();
        }
        return 0;
    }

    private void initRoutesList(Location newLocation) {
        for (Route route : dataProvider.getAllRoutes()) {
            dataProvider.addRoute(newLocation.getUserId(), route);
        }
    }
}
