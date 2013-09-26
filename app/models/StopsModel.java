package models;

import java.util.List;

public class StopsModel {
    public List<StopsModel.Route> routes;
    
    public static class Route {
        public String routeName;
        public List<Stop> stops;
    }
    
    public static class Stop {
        public String stopName;
        public List<Integer> predictions;
    }
}
