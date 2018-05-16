package springbootdemo.demo.ghost;

import org.springframework.beans.factory.annotation.Autowired;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Direction;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

public class GhostThread extends Thread {

    @Autowired
    DirectionsService directionsService;

    private String userId;
    private ArrayList<BusStop> busStops;
    private Direction[] directions;

    private Route route;

    public GhostThread(Route route) {
        this.route = route;

        initUserId();
        initBusStops();
        initDirections();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    private void initBusStops() {

    }

    private void initDirections() {
        directions = directionsService.getDirections(route);
    }

    private void initUserId() {
        userId = route.getName();
    }


}
