package controllers;

import nextbus.NextBusService;
import nextbus.NextBusServiceImpl;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("SF Muni Big Screen"));
    }
    
    public static Result stops() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting lat and lon parameters");
        } else {
            double lat, lon;
            try {
                lat = json.get("lat").asDouble();
                lon = json.get("lon").asDouble();
            } catch (Exception e) {
                return badRequest("Invalid value for lat/lon parameters");
            }
            
            ObjectMapper mapper = new ObjectMapper();
            NextBusService service = new NextBusServiceImpl();

            return ok(mapper.valueToTree(service.getStops(lat, lon)));
        }
    }

}
