package springbootdemo.demo.locationBusiness.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
@Service
public class DataProvider implements IDataProvider {

    @Autowired
    public LocationRepository locationRepository;

    @Autowired
    private BusStopRepository busStopRepository;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private RouteBusStopRepository routeBusStopRepository;

    @Autowired
    private LocationRouteRepository locationRouteRepository;

    @Autowired
    private LocationBusStopRepository locationBusStopRepository;

    @Override
    public boolean hasLocationWithUserId(String userId) {
        return locationRepository.findByUserId(userId) != null;
    }

    @Override
    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void addBusStopToEnd(String userId, BusStop busStop) {
        Location location = locationRepository.findByUserId(userId);

        ArrayList<LocationBusStop> prevLocationBusStops = locationBusStopRepository.findByLocationId(location.getId());
        int currentPosition = prevLocationBusStops.size();

        LocationBusStop locationBusStop = new LocationBusStop();
        locationBusStop.setLocationId(location.getId());
        locationBusStop.setPosition(currentPosition);
        locationBusStop.setBusStop(busStop.getId());

        locationBusStopRepository.save(locationBusStop);
    }

    @Override
    public void removeBusStop(String userId, BusStop busStop) {
        Location location = locationRepository.findByUserId(userId);
        if (location != null) {
            locationRepository.delete(location);
        }
    }

    @Override
    public void addRoute(@NotNull String userId, Route route) {
        Location location = locationRepository.findByUserId(userId);

        LocationRoute locationRoute = new LocationRoute();
        locationRoute.setLocationId(location.getId());
        locationRoute.setRouteId(route.getId());

        locationRouteRepository.save(locationRoute);
    }

    @Override
    public void removeRoute(String userId, Route route) {
        Location location = locationRepository.findByUserId(userId);

        LocationRoute locationRoute = locationRouteRepository.findByLocationIdAndRouteId(
                location.getId(),
                route.getId()
        );

        if (locationRoute != null) {
            locationRouteRepository.save(locationRoute);
        }
    }

    @Override
    public ArrayList<Route> getRoutes(String userId) {
        return new ArrayList<>(locationRepository.findByUserId(userId).getRoutes());
    }

    @Override
    public ArrayList<BusStop> getBusStopList(String userId) {
        return new ArrayList<>(locationRepository.findByUserId(userId).getBusStops());
    }

    @Override
    public ArrayList<Route> getAllRoutes() {
        return (ArrayList<Route>) routesRepository.findAll();
    }

    @Override
    public ArrayList<BusStop> getRouteBusStop(Route route) {
        List<RouteBusStops> routeBusStops = routeBusStopRepository.findRouteBusStopsByRouteId(route.getId());
        ArrayList<BusStop> result = new ArrayList<>();

        for (RouteBusStops routeBusStop : routeBusStops) {
            BusStop newBusStop = routeBusStop.getBusStop();
            result.add(newBusStop);
        }

        return result;
    }

    @Override
    public ArrayList<BusStop> getAllBusStop() {
        return (ArrayList<BusStop>) busStopRepository.findAll();
    }
}
