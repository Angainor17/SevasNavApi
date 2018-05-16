package springbootdemo.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.locationBusiness.LocationProxy;
import springbootdemo.demo.locationBusiness.model.UncheckedUserLocation;
import springbootdemo.demo.models.Location;
import springbootdemo.demo.repositories.LocationRepository;
import springbootdemo.demo.webSockets.WebSocketResponse;

import java.util.ArrayList;

@Controller
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationProxy locationManager;

    @Autowired
    private SimpMessagingTemplate simpTemplate;

    @RequestMapping(value = "/sendLocation", method = RequestMethod.POST)
    @ResponseBody
    public String setUserLocation(@RequestBody UncheckedUserLocation requestBody) {
        locationManager.addNewLocation(requestBody);

        simpTemplate.convertAndSend("/topic/greetings", getMap());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(
                    locationManager.getRoutesList(requestBody.getUserId())
            );
        } catch (JsonProcessingException e) {
            return "[]";
        }
    }

    private WebSocketResponse getMap() {
        ArrayList<Location> locations = (ArrayList<Location>) locationRepository.findAll();
        return new WebSocketResponse(locations);
    }
}
