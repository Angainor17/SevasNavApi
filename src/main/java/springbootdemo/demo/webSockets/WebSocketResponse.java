package springbootdemo.demo.webSockets;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import springbootdemo.demo.models.Location;

import java.util.ArrayList;

public class WebSocketResponse {

    @JsonSerialize
    ArrayList<LocationApi> locations;

    public WebSocketResponse(ArrayList<Location> locations) {
        this.locations = new ArrayList<>();
        for(Location location: locations){
            this.locations.add(new LocationApi(location));
        }
    }
}
