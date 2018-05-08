package springbootdemo.demo.locationBusiness.data;

import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

public interface IDataProvider {

    void addBusStopToEnd(String userId, BusStop busStop);

    void addLocation(Location location);

    void removeBusStop(String userId, BusStop busStop);

    void addRoute(String userId, Route route);

    void removeRoute(String userId, Route route);

    boolean hasLocationWithUserId(String userId);

    ArrayList<Route> getRoutes(String userId);

    ArrayList<BusStop> getBusStopList(String userId);

    ArrayList<BusStop> getRouteBusStop(Route route);

    ArrayList<Route> getAllRoutes();

    ArrayList<BusStop> getAllBusStop();
}
