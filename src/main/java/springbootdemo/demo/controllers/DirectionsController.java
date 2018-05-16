package springbootdemo.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springbootdemo.demo.directions.CoordinatesModel;
import springbootdemo.demo.directions.DirectionApiBodyModel;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Direction;
import springbootdemo.demo.models.Route;
import springbootdemo.demo.models.RouteDirection;
import springbootdemo.demo.repositories.DirectionRepository;
import springbootdemo.demo.repositories.RouteBusStopRepository;
import springbootdemo.demo.repositories.RouteDirectionRepository;
import springbootdemo.demo.repositories.RoutesRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Controller
public class DirectionsController {

    @Autowired
    IDataProvider dataProvider;

    @Autowired
    RouteBusStopRepository routeBusStopRepository;

    @Autowired
    DirectionRepository directionRepository;

    @Autowired
    RouteDirectionRepository routeDirectionRepository;

    @Autowired
    RoutesRepository routesRepository;


    static ArrayList<Direction> directions;

    @RequestMapping(value = "/getRoutesBusStops", method = RequestMethod.GET)
    @ResponseBody
    public String getRoutesBusStops() {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<Route, ArrayList<BusStop>> hashMap = dataProvider.getRoutesWithBusStops();

        ArrayList<AllRoutesBusStops> result = new ArrayList<>();
        for (HashMap.Entry<Route, ArrayList<BusStop>> entry : hashMap.entrySet()) {
            result.add(new AllRoutesBusStops(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @RequestMapping(value = "/getRouteDirection/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getRouteDirection(@PathVariable String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Route route = routesRepository.findById(Integer.parseInt(id)).get(0);

        ArrayList<RouteDirection> routeDirections = routeDirectionRepository.findRouteDirectionByRouteId(route.getId());
        routeDirections.sort(Comparator.comparing(RouteDirection::getPosition));
        ArrayList<Direction> directions = new ArrayList<>();

        for (RouteDirection routeDirection : routeDirections) {
            directions.add(routeDirection.getDirection());
        }

        try {
            return objectMapper.writeValueAsString(directions);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @RequestMapping(value = "/sendDirection", method = RequestMethod.POST)
    @ResponseBody
    public String sendDirection(@RequestBody DirectionApiBodyModel directionApiBodyModel) {
        if (directions == null) {
            directions = new ArrayList<>();
        }

        ArrayList<CoordinatesModel> coordinatesModels = directionApiBodyModel.getPoints();
        for (int position = 0; position < coordinatesModels.size(); position++) {

            CoordinatesModel coordinatesModel = coordinatesModels.get(position);

            Direction direction = new Direction(
                    coordinatesModel.getLongitude(),
                    coordinatesModel.getLatitude()
            );
            direction = directionRepository.save(direction);

            int directionId = direction.getId();

            RouteDirection routeDirection = new RouteDirection();
            routeDirection.setDirectionId(directionId);
            routeDirection.setPosition(position);
            routeDirection.setRouteId(directionApiBodyModel.getRouteId());

            routeDirection = routeDirectionRepository.save(routeDirection);
        }

        return "";
    }

    private class AllRoutesBusStops {
        @JsonSerialize
        Route route;

        @JsonSerialize
        ArrayList<BusStop> busStops;

        public AllRoutesBusStops(Route route, ArrayList<BusStop> busStops) {
            this.route = route;
            this.busStops = busStops;
        }
    }
}
