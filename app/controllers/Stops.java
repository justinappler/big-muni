package controllers;

import java.util.Map;

import models.StopsModel;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Stops controller
 * 
 * Serves the stops JSON API
 * 
 * @author justinappler
 */
public class Stops extends Controller {

    /**
     * JSON API that accepts a lat and lon parameter, returns
     * arrival predictions for nearby stops
     * 
     * @return a list of nearby stops and theirs predictions
     */
    public static Result stops() {
        Map<String, String[]> queryString = request().queryString();
        
        if (queryString == null) {
            return badRequest("Expecting lat and lon parameters");
        } else {
            double lat, lon;
            try {
                lat = Double.parseDouble(queryString.get("lat")[0]);
                lon = Double.parseDouble(queryString.get("lon")[0]);
            } catch (Exception e) {
                return badRequest("Invalid value for lat/lon parameters");
            }

            StopsModel stopsModel = StopsModel.getNearby(lat, lon);
            
            return ok(new ObjectMapper().valueToTree(stopsModel));
        }
    }
}
