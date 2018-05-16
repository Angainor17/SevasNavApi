package springbootdemo.demo.locationBusiness.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.*;
import springbootdemo.demo.repositories.*;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class DataProvider implements IDataProvider {

    private static ArrayList<BusStop> allBusStops;
    private static ArrayList<Route> allRoutes;
    private static HashMap<Route, ArrayList<BusStop>> routeBusStops;

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
    public Location getLocation(String userId) {
        return locationRepository.findByUserId(userId);
    }

    @Override
    public Location updateLocation(UncheckedUserLocation uncheckedUserLocation) {
        Location location = getLocation(uncheckedUserLocation.getUserId());
        location.update(uncheckedUserLocation);
        locationRepository.save(location);

        return location;
    }

    @Override
    public void addLocation(Location location) {
        location.setFirstTime("" + location.getTime());
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
        ArrayList<LocationBusStop> locationBusStop = locationBusStopRepository.findByLocationIdAndBusStopIds(
                location.getId(),
                busStop.getId()
        );
        if (!locationBusStop.isEmpty()) {
            locationBusStopRepository.delete(locationBusStop.get(0));
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
            locationRouteRepository.delete(locationRoute);
        }
    }

    @Override
    public ArrayList<Route> getRoutes(String userId) {
        Location location = locationRepository.findByUserId(userId);

        if (location != null) {
            Set<Route> routeSet = location.getRoutes();
            if (routeSet != null) {
                return new ArrayList<>(routeSet);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public ArrayList<BusStop> getBusStopList(String userId) {
        Location location = locationRepository.findByUserId(userId);

        if (location == null) {
            return new ArrayList<>();
        }

        ArrayList<LocationBusStop> busStopOrder = locationBusStopRepository.findByLocationId(location.getId());

        if (busStopOrder != null) {

            busStopOrder.sort(Comparator.comparing(LocationBusStop::getPosition));

            ArrayList<BusStop> result = new ArrayList<>();

            for (LocationBusStop locationBusStop : busStopOrder) {
                BusStop busStop = busStopRepository.findById(locationBusStop.getBusStopID());
                result.add(busStop);
            }
            return result;
        }
        return new ArrayList<>();
    }

    @Override
    public void updateLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void removeLocation(String userId) {
        Location location = locationRepository.findByUserId(userId);
        if (location != null) {
            locationRepository.delete(location);
        }
    }

    @Override
    public ArrayList<Route> getAllRoutes() {
        if (allRoutes == null) {
            allRoutes = (ArrayList<Route>) routesRepository.findAll();
        }
        return allRoutes;
    }

    @Override
    public ArrayList<BusStop> getRouteBusStop(Route route) {
        return getRoutesWithBusStops().get(getRoute(route));
    }

    @Override
    public HashMap<Route, ArrayList<BusStop>> getRoutesWithBusStops() {
        if (routeBusStops == null) {
            routeBusStops = new HashMap<>();

            for (Route route1 : getAllRoutes()) {
                List<RouteBusStops> routeBusStop = routeBusStopRepository.findRouteBusStopsByRouteId(route1.getId());
                routeBusStop.sort(Comparator.comparing(RouteBusStops::getPosition));//check!!!

                ArrayList<BusStop> result = new ArrayList<>();

                for (RouteBusStops routeBusStopItem : routeBusStop) {
                    result.add(routeBusStopItem.getBusStop());
                }

                routeBusStops.put(route1, result);
            }
        }

        return routeBusStops;
    }

    private Route getRoute(Route route) {
        ArrayList<Route> routes = getAllRoutes();

        for (Route routeFromList : routes) {
            if (routeFromList.getId().equals(route.getId())) {
                return routeFromList;
            }
        }
        return null;
    }

    @Override
    public ArrayList<BusStop> getAllBusStop() {
        if (allBusStops == null) {
            allBusStops = (ArrayList<BusStop>) busStopRepository.findAll();
        }
        return allBusStops;
    }
}
