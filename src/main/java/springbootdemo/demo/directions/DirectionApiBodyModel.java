package springbootdemo.demo.directions;

import java.util.ArrayList;

public class DirectionApiBodyModel {

    private Integer routeId;
    private ArrayList<CoordinatesModel> points;

    public Integer getRouteId() {
        return routeId;
    }

    public ArrayList<CoordinatesModel> getPoints() {
        return points;
    }
}
