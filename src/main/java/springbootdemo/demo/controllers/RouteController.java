package springbootdemo.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.models.*;
import springbootdemo.demo.repositories.BusStopRepository;
import springbootdemo.demo.repositories.RouteBusStopRepository;
import springbootdemo.demo.repositories.RoutesRepository;

import java.util.ArrayList;
import java.util.Comparator;

@Controller
public class RouteController {

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private RouteBusStopRepository routeBusStopRepository;

    @Autowired
    private BusStopRepository busStopRepository;

    @RequestMapping(value = "/getRoutes", method = RequestMethod.GET)
    @ResponseBody
    public String getBusStops() {
        ObjectMapper objectMapper = new ObjectMapper();
        Iterable<Route> routes = routesRepository.findAll();
        try {
            return objectMapper.writeValueAsString(routes);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @RequestMapping(value = "/getRoute/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getRoute(@PathVariable("id") int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Route route = routesRepository.findById(id).get(0);
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(route);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @RequestMapping(value = "/getRouteDetail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getRouteDetail(@PathVariable("id") int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<RouteBusStops> routeBusStops = (ArrayList<RouteBusStops>) routeBusStopRepository.findRouteBusStopsByRouteId(id);

        routeBusStops.sort(Comparator.comparing(RouteBusStops::getPosition));
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(routeBusStops);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
