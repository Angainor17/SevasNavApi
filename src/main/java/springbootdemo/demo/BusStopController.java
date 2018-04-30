package springbootdemo.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springbootdemo.demo.models.BusStop;
import springbootdemo.demo.models.BusStopRepository;

@Controller
public class BusStopController {

    @Autowired
    private BusStopRepository busStopRepository;

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
}
