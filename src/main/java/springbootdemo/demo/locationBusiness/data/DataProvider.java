package springbootdemo.demo.locationBusiness.data;

import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

public class DataProvider implements IDataProvider {

    @Override
    public void addBusStopToEnd(int userId, BusStop busStop, int position) {

    }

    @Override
    public void removeBusStop(int userId, BusStop busStop) {

    }

    @Override
    public void addRoute(int userId, Route route) {

    }

    @Override
    public void removeRoute(int userId, Route route) {

    }

    @Override
    public ArrayList<Route> getRoutes(int userId) {
        return null;
    }

    @Override
    public ArrayList<Route> getBusStopList(int userId) {
        return null;
    }

    @Override
    public ArrayList<Route> getAllRoutes() {
        return null;
    }

    @Override
    public ArrayList<Route> getAllBusStop() {
        return null;
    }
}
