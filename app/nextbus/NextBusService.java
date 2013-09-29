package nextbus;

import java.util.List;

import nextbus.api.PredictionList;
import nextbus.api.Route;
import nextbus.api.Stop;

import org.apache.commons.lang3.tuple.Pair;

/**
 * NextBus service interface
 * @author justinappler
 */
public interface NextBusService {

    /**
     * Gets a list of predictions for a list of stops
     * @param stops
     * @return
     */
    public PredictionList getPredictionListsForRoutes(List<Pair<Stop, Route>> stops);

    /**
     * Given a location and distance (or radius), returns
     * all nearby stops and their corresponding routes
     * 
     * @param latitude
     * @param longitude
     * @param distance radius in kilometers
     * @return
     */
    public List<Pair<Stop, Route>> getNearbyStops(double latitude, double longitude,
            double distance);
}
