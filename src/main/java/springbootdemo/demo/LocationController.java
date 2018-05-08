package springbootdemo.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.locationBusiness.UsersLocationManager;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.models.LocationRepository;

import java.util.ArrayList;

@Controller
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    private UsersLocationManager locationManager;

    public LocationController() {
        locationManager = new UsersLocationManager();
    }

    @RequestMapping(value = "/getMap", method = RequestMethod.GET)
    @ResponseBody
    public String getBusStops() {
        ArrayList<Location> locations = (ArrayList<Location>) locationRepository.findAll();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(locations);
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    @RequestMapping(value = "/sendLocation", method = RequestMethod.POST)
    @ResponseBody
    public String setUserLocation(@RequestBody UncheckedUserLocation requestBody) {
        locationManager.addNewLocation(requestBody);
        return "";
    }

}
