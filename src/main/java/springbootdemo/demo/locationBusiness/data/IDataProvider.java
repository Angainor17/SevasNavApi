package springbootdemo.demo.locationBusiness.data;

import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

public interface IDataProvider {

    void addBusStopToEnd(int userId, BusStop busStop, int position);

    void removeBusStop(int userId, BusStop busStop);

    void addRoute(int userId, Route route);

    void removeRoute(int userId, Route route);

    ArrayList<Route> getRoutes(int userId);

    ArrayList<Route> getBusStopList(int userId);

    ArrayList<Route> getAllRoutes();

    ArrayList<Route> getAllBusStop();
}
