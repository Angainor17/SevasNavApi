package springbootdemo.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.locationBusiness.data.IDataProvider;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.repositories.BusStopRepository;
import springbootdemo.demo.repositories.RouteBusStopRepository;
import springbootdemo.demo.repositories.RoutesRepository;

@Controller
public class BusStopController {

    @Autowired
    private BusStopRepository busStopRepository;

    @Autowired
    IDataProvider dataProvider;

    @Autowired
    RouteBusStopRepository routeBusStopRepository;

    @Autowired
    RoutesRepository routesRepository;

    @RequestMapping(value = "/getBusStops", method = RequestMethod.GET)
    @ResponseBody
    public String getBusStops() {
        ObjectMapper objectMapper = new ObjectMapper();
        Iterable<BusStop> busStops = busStopRepository.findAll();

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(busStops);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @RequestMapping(value = "/getBusStops/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getBusStopByRouteId(@PathVariable Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(dataProvider.getRouteBusStop(
                    routesRepository.findById(id).get(0)
            ));
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }
}
