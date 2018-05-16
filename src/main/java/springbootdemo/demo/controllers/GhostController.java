package springbootdemo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.ghost.GhostThread;
import springbootdemo.demo.models.Route;
import springbootdemo.demo.repositories.RoutesRepository;

@Controller
public class GhostController {

    @Autowired
    RoutesRepository routesRepository;

    @RequestMapping(value = "/startGhost/{routeNumber}", method = RequestMethod.GET)
    @ResponseBody
    public String getBusStops(@PathVariable("routeNumber") String routeNumber) {
        Route route = routesRepository.findByName("" + routeNumber);

        GhostThread ghostThread = new GhostThread(route);
        ghostThread.start();

        return "Я жажду служить!";
    }
}
