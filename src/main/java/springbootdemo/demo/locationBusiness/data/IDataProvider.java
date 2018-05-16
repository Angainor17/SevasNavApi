package springbootdemo.demo.locationBusiness.data;

import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;
import java.util.HashMap;

public interface IDataProvider {

    HashMap<Route, ArrayList<BusStop>> getRoutesWithBusStops();

    void addBusStopToEnd(String userId, BusStop busStop);

    void addLocation(Location location);

    void addRoute(String userId, Route route);


    void removeBusStop(String userId, BusStop busStop);

    void removeLocation(String userId);

    void removeRoute(String userId, Route route);


    boolean hasLocationWithUserId(String userId);


    Location getLocation(String userId);

    ArrayList<Route> getRoutes(String userId);

    ArrayList<BusStop> getBusStopList(String userId);

    ArrayList<BusStop> getRouteBusStop(Route route);

    ArrayList<Route> getAllRoutes();

    ArrayList<BusStop> getAllBusStop();


    Location updateLocation(UncheckedUserLocation location);

    void updateLocation(Location location);
}
