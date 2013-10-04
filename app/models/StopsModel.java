package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nextbus.NextBusService;
import nextbus.impl.NextBusServiceImpl;
import nextbus.models.PredictionList;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Output model for the Stops API
 * @author justinappler
 */
public class StopsModel {
    
    private static final double NEARBY = 0.25; // .25KM or ~2 City Blocks
    
    public static StopsModel getNearby(double lat, double lon) {
        NextBusService service = NextBusServiceImpl.getInstance();
        
        List<Pair<nextbus.models.Stop, nextbus.models.Route>> nearbyStops = service.getNearbyStops(lat, lon, NEARBY);
        PredictionList predictionList = service.getPredictionListsForRoutes(nearbyStops);
        
        return new StopsModel(nearbyStops, predictionList);
    }
    
    /**
     * Constructor for the stops service model.  Takes a list of nearby stops and
     * a matching set of predictions
     * 
     * @param nearbyStops
     * @param predictionList
     */
    public StopsModel(List<Pair<nextbus.models.Stop, nextbus.models.Route>> nearbyStops, PredictionList predictionList) {
        
        // Create a map between routes and it's stops
        Map<nextbus.models.Route, List<nextbus.models.Stop>> routeMap = new HashMap<nextbus.models.Route,List<nextbus.models.Stop>>();
        for (Pair<nextbus.models.Stop, nextbus.models.Route> stop : nearbyStops) {
            if (routeMap.get(stop.getRight()) == null) {
                routeMap.put(stop.getRight(), new ArrayList<nextbus.models.Stop>());
            }
            routeMap.get(stop.getRight()).add(stop.getLeft());
        }
        
        routes = new ArrayList<StopsModel.Route>();
        
        for (nextbus.models.Route route : routeMap.keySet()) {
            StopsModel.Route modelRoute = new StopsModel.Route();
            modelRoute.routeName = route.title;
            
            List<StopsModel.Stop> modelStops = new ArrayList<StopsModel.Stop>();
            for (nextbus.models.Stop stop : routeMap.get(route)) {
                StopsModel.Stop modelStop = new StopsModel.Stop();
                modelStop.stopName = stop.title;
                modelStop.predictions = new ArrayList<Integer>();
                
                modelStop.predictions.addAll(predictionList.getPredictionsForStop(stop));
                
                modelStops.add(modelStop);
            }
            
            modelRoute.stops = modelStops;
            routes.add(modelRoute);
        }
    }

    public List<StopsModel.Route> routes;
    
    /**
     * Representation of a 'route', with the name
     * and all nearby stops
     */
    public static class Route {
        public String routeName;
        public List<Stop> stops;
    }
    
    /**
     * Representation of a Stop, with the name of
     * the stop and all available predictions
     */
    public static class Stop {
        public String stopName;
        public List<Integer> predictions;
    }
}
