package springbootdemo.demo.locationBusiness.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class BusStopManager {

    @Autowired
    private IDataProvider dataProvider;

    public void addNewBusStop(Location location, ArrayList<BusStop> busStops) {
        String userId = location.getUserId();
        if (busStops.isEmpty()) {
            return;
        }

        removeWrongBusStops(location, busStops);

        ArrayList<BusStop> lastBusStops = getLastBusStops(userId, busStops.size());

        for (BusStop newBusStop : busStops) {
            ArrayList<BusStop> prevBusStops = dataProvider.getBusStopList(userId);
            if (prevBusStops != null && !prevBusStops.isEmpty()) {

                if (contains(lastBusStops, newBusStop)) {
                    continue;
                }
                BusStop lastBusStop = prevBusStops.get(prevBusStops.size() - 1);

                if (!lastBusStop.equals(newBusStop)) {
                    dataProvider.addBusStopToEnd(userId, newBusStop);
                }
            } else {
                dataProvider.addBusStopToEnd(userId, newBusStop);
            }
        }
    }

    private void removeWrongBusStops(Location location, ArrayList<BusStop> busStops) {
        if (getCurrentBusStops(location).size() < 3) {
            return;
        }

        for (int i = 0; i < busStops.size(); i++) {
            if (canBusStopBeAdded(location, busStops.get(i))) {
                busStops.remove(i);
                i--;
            }
        }
    }

    private boolean canBusStopBeAdded(Location location, BusStop newBusStop) {
        ArrayList<Route> routes = getCurrentRoutes(location);
        ArrayList<BusStop> lastBusStops = getLastBusStops(location.getUserId(), 2);

        for (Route route : routes) {

            for (BusStop lastBusStop : lastBusStops) {
                ArrayList<BusStop> futureBusStops = getFutureBusStops(route, lastBusStop, 2);
                boolean isContains = contains(futureBusStops, newBusStop);
                if (isContains) {
                    return true;
                }
            }
        }

        return false;
    }

    private ArrayList<BusStop> getFutureBusStops(Route route, BusStop busStop, int count) {
        ArrayList<BusStop> allBusStops = dataProvider.getRouteBusStop(route);

        for (int i = 0; i < allBusStops.size() - 1; i++) {
            if (allBusStops.get(i).equals(busStop)) {

                return new ArrayList<>(allBusStops.subList(
                        i, Math.min(allBusStops.size(), i + count))
                );
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<BusStop> getLastBusStops(String userId, int count) {
        if (count == 0) {
            return new ArrayList<>();
        }

        ArrayList<BusStop> lastBusStops = dataProvider.getBusStopList(userId);
        if (lastBusStops.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(lastBusStops.subList(
                Math.max(0, lastBusStops.size() - count),
                Math.max(lastBusStops.size() - 1, 1)
        ));
    }

    public void removeUselessBusStops(Location location) {
        deleteBusStopsNoOneRouteBelong(location);

        deleteParallelBusStops(location);
    }

    private void deleteParallelBusStops(Location location) {
        ArrayList<BusStop> busStops = getCurrentBusStops(location);

        if (busStops.size() < 3) {
            return;
        }

        for (int i = 0; i < busStops.size() - 2; i++) {
            BusStop busStop = busStops.get(i);
            HashMap<Route, ArrayList<BusStop>> localBusStopMap = getNextBusStopMap(location, busStop);

            boolean hasLocalBusStops = false;
            for (HashMap.Entry<Route, ArrayList<BusStop>> entry : localBusStopMap.entrySet()) {
                for (int j = i + 1; j < busStops.size(); j++) {
                    if (contains(entry.getValue(), busStops.get(j))) {
                        hasLocalBusStops = true;
                        break;
                    }

                }
                if (hasLocalBusStops) {
                    break;
                }
            }

            if (!hasLocalBusStops) {
                dataProvider.removeBusStop(location.getUserId(), busStop);
                i--;
            }
        }
    }

    private boolean contains(ArrayList<BusStop> busStops, BusStop busStop) {
        for (BusStop compareBusStop : busStops) {
            if (compareBusStop.equals(busStop)) {
                return true;
            }
        }

        return false;
    }

    private HashMap<Route, ArrayList<BusStop>> getNextBusStopMap(Location location, BusStop busStop) {
        HashMap<Route, ArrayList<BusStop>> hashMap = new HashMap<>();
        ArrayList<Route> routes = getCurrentRoutes(location);

        for (Route route : routes) {
            hashMap.put(route, getLocalBusStop(busStop, route));
        }

        return hashMap;
    }

    private ArrayList<BusStop> getLocalBusStop(BusStop busStop, Route route) {
        ArrayList<BusStop> routesBusStops = dataProvider.getRouteBusStop(route);
        ArrayList<BusStop> nextBusStops = new ArrayList<>();

        final int routesSize = routesBusStops.size();

        for (int i = 0; i < routesSize; i++) {
            BusStop potentialNextBusStop = routesBusStops.get(i);
            if (!potentialNextBusStop.equals(busStop)) {
                continue;
            }

            if (i < routesSize - 2) {
                nextBusStops.add(routesBusStops.get(i + 1));
                nextBusStops.add(routesBusStops.get(i + 2));
                break;
            }

            if (i < routesSize - 1) {
                nextBusStops.add(routesBusStops.get(i + 1));
                break;
            }
        }

        return nextBusStops;
    }

    private void deleteBusStopsNoOneRouteBelong(Location location) {
        for (BusStop locationBusStop : getCurrentBusStops(location)) {
            boolean hasOneRouteCurrentBusStop = false;

            for (Route route : getCurrentRoutes(location)) {
                ArrayList<BusStop> selectedRouteBusStops = dataProvider.getRouteBusStop(route);

                for (BusStop routeBusStop : selectedRouteBusStops) {
                    if (routeBusStop.equals(locationBusStop)) {
                        hasOneRouteCurrentBusStop = true;
                        break;
                    }
                }

                if (hasOneRouteCurrentBusStop) {
                    break;
                }
            }

            if (!hasOneRouteCurrentBusStop) {
                dataProvider.removeBusStop(location.getUserId(), locationBusStop);
            }
        }
    }

    private ArrayList<Route> getCurrentRoutes(Location location) {
        return dataProvider.getRoutes(location.getUserId());
    }

    private ArrayList<BusStop> getCurrentBusStops(Location location) {
        return dataProvider.getBusStopList(location.getUserId());
    }
}
