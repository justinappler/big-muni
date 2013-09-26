package nextbus;

import models.StopsModel;

public interface NextBusService {

    /**
     * Returns the nearby stops and their predictions
     * 
     * @param latitude
     * @param longitude
     * @param distance
     * @return
     */
    public StopsModel getStops(double latitude, double longitude);
}
