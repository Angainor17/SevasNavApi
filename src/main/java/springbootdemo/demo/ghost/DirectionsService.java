package springbootdemo.demo.ghost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Direction;
import springbootdemo.demo.models.Route;
import springbootdemo.demo.models.RouteDirection;
import springbootdemo.demo.repositories.DirectionRepository;
import springbootdemo.demo.repositories.RouteDirectionRepository;
import springbootdemo.demo.repositories.RoutesRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Service
public class DirectionsService {

    @Autowired
    private DirectionRepository directionRepository;

    @Autowired
    private RouteDirectionRepository routeDirectionRepository;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private IDataProvider dataProvider;

    private static HashMap<Integer, Direction[]> routeHashMap;

    public Direction[] getDirections(Route route) {
        return getRouteHashMap().get(route.getId());
    }

    private HashMap<Integer, Direction[]> getRouteHashMap() {
        if (routeHashMap == null) {
            initRouteHashMap();
        }
        return routeHashMap;
    }

    private void initRouteHashMap() {
        routeHashMap = new HashMap<>();

        ArrayList<Route> routes = (ArrayList<Route>) routesRepository.findAll();

        for (Route route : routes) {
            try {
                Integer routeId = route.getId();
                routeHashMap.put(
                        routeId,
                        getDirections(routeId)
                );
            } catch (Exception e) {

            }
        }
    }

    private Direction[] getDirections(Integer routeId) {
        ArrayList<RouteDirection> routeDirections = routeDirectionRepository.findRouteDirectionByRouteId(routeId);
        routeDirections.sort(Comparator.comparing(RouteDirection::getPosition));

        int lastBusStop = getLastBusStopIndex(routeId,new ArrayList<>());//fix
        Direction[] directions = new Direction[lastBusStop];

        for (int i = 0; i < lastBusStop; i++) {
            directions[i] = routeDirections.get(i).getDirection();

        }

        return directions;
    }

    private int getLastBusStopIndex(Integer routeId, ArrayList<RouteDirection> routeDirections) {
        Route route = routesRepository.findById(routeId).get(0);
        ArrayList<BusStop> busStops = dataProvider.getRouteBusStop(route);

//        if(busStops.get(0).equals())
//
//        {
//
//
//        }
        for (int i = 0; i < routeDirections.size(); i++) {
        }
return 0;
//        return busStops.get(0).equals(busStops.get(busStops.size() - 1));
    }
}
