package springbootdemo.demo.locationBusiness.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.Route;

import java.util.ArrayList;

@Service
public class RouteManager {

    private final int BUS_STOPS_COUNT_TO_DELETE = 2;//2
    private final int BUS_STOPS_CAN_BE_MISSED = 1;
    private final int WRONG_BUS_STOPS_CAN_BE_ADDED = 1;

    @Autowired
    private IDataProvider dataProvider;

    public void removeUselessRoutes(Location location) {
        deleteRouteHasNoCheckedBusStops(location);
//        deleteRoutesWithWrongBusStopPosition(location);
    }

    private void deleteRoutesWithWrongBusStopPosition(Location location) {
        ArrayList<BusStop> correctBusStopSequence = getCurrentBusStops(location);

        if (correctBusStopSequence.size() < 3) {
            return;
        }

        for (Route route : getCurrentRoutes(location)) {
            boolean hasCorrectSequence = false;

            ArrayList<BusStop> selectedRouteBusStops = dataProvider.getRouteBusStop(route);

            int correctSequenceIndex = 0;
            for (int routeBusStopIndex = 0; routeBusStopIndex < selectedRouteBusStops.size(); routeBusStopIndex++) {
                BusStop selectedRouteBusStop = selectedRouteBusStops.get(routeBusStopIndex);

                BusStop correctSequenceBusStop = correctBusStopSequence.get(correctSequenceIndex);

                if (selectedRouteBusStop.equals(correctSequenceBusStop)) {
                    if (correctSequenceIndex == correctBusStopSequence.size() - 1) {
                        hasCorrectSequence = true;
                        break;
                    }
                    correctSequenceIndex++;
                } else {
                    boolean correctSequenceHasNextItem = correctSequenceIndex + 1 < correctBusStopSequence.size();
                    boolean selectedRouteHasNextItem = routeBusStopIndex + 1 < selectedRouteBusStops.size();

                    if (correctSequenceHasNextItem && selectedRouteHasNextItem) {
                        BusStop nextCorrectSequenceBusStop = correctBusStopSequence.get(correctSequenceIndex + 1);
                        BusStop nextSelectedRouteBusStop = selectedRouteBusStops.get(routeBusStopIndex + 1);

                        boolean realBusStopMissed = nextSelectedRouteBusStop.equals(correctSequenceBusStop);
                        boolean wrongBusStopAdded = nextCorrectSequenceBusStop.equals(selectedRouteBusStop);

                        if (realBusStopMissed) {
                            routeBusStopIndex++;
                        }

                        if (wrongBusStopAdded) {
                            correctSequenceIndex++;
                        }
                    } else {
                        break;
                    }
                }
            }

            if (!hasCorrectSequence) {
                dataProvider.removeRoute(location.getUserId(), route);
            }
        }
    }

    private void deleteRouteHasNoCheckedBusStops(Location location) {
        ArrayList<Route> routes = getCurrentRoutes(location);
        ArrayList<BusStop> busStops = getCurrentBusStops(location);

        for (Route route : routes) {

            int consistCount = 0;
            ArrayList<BusStop> selectedRouteBusStops = dataProvider.getRouteBusStop(route);

            for (BusStop selectedRouteBusStop : selectedRouteBusStops) {

                for (BusStop busStop : busStops) {
                    if (busStop.equals(selectedRouteBusStop)) {
                        consistCount++;
                        break;
                    }
                }
            }

            if (busStops.size() - consistCount >= BUS_STOPS_COUNT_TO_DELETE) {
                dataProvider.removeRoute(location.getUserId(), route);
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
