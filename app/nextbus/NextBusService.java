package nextbus;

import models.StopsModel;

/**
 * NextBus service interface
 * @author justinappler
 */
public interface NextBusService {

    /**
     * Returns the nearby stops and their predictions
     * 
     * @param latitude
     * @param longitude
     * @return nearby stops and their predictions
     */
    public StopsModel getStops(double latitude, double longitude);
}
