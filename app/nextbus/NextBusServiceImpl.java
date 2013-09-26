package nextbus;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXB;

import models.StopsModel;
import nextbus.Route.Stop;

import org.apache.commons.lang3.tuple.Pair;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;
import util.GeoUtil;

public class NextBusServiceImpl implements NextBusService {

    private List<Route> allRoutes;
        
    private static final String NEXTBUS_PUBLIC_XML_FEED = "http://webservices.nextbus.com/service/publicXMLFeed";
    
    private static final String COMMAND = "command";
    private static final String AGENCY = "a";
    private static final String ROUTE = "r";
    private static final String STOPS = "stops";
    
    private static final String ROUTE_LIST_COMMAND = "routeList";
    private static final String ROUTE_CONFIG_COMMAND = "routeConfig";
    private static final String PREDICT_MULTIPLE_STOPS_COMMAND = "predictionsForMultiStops";
    private static final String SF_MUNI_AGENCY = "sf-muni";

    private static final double NEARBY = 0.25; // .45KM or ~2 City Blocks
    
    
    public NextBusServiceImpl() {        
        allRoutes = RouteCache.getRoutes();
        if (allRoutes == null) {
            allRoutes = new ArrayList<Route>();
            try {
                populateRouteList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void populateRouteList() throws Exception {
        Promise<Response> routeListResponse = WS
                .url(NEXTBUS_PUBLIC_XML_FEED)
                .setQueryParameter(COMMAND, ROUTE_LIST_COMMAND)
                .setQueryParameter(AGENCY, SF_MUNI_AGENCY)
                .get();
        
        routeListResponse.onRedeem(new Callback<Response>() {

            @Override
            public void invoke(Response response) throws Throwable {
                String routeXml = response.getBody();

                RouteList routes = JAXB.unmarshal(new StringReader(routeXml), RouteList.class);
                
                LinkedList<Route> routeNames = new LinkedList<Route>();
                
                for (Route route : routes.route) {
                    if (route != null)
                        routeNames.add(route);
                }
                
                populateRoutes(routeNames);
            }
        });
    }
    
    private void populateRoutes(final LinkedList<Route> routeNames) {
        if (routeNames.isEmpty()) {
            RouteCache.cache(allRoutes);
        } else {
            Route nextRoute = routeNames.pop();
            
            Promise<Response> routeListResponse = WS
                    .url(NEXTBUS_PUBLIC_XML_FEED)
                    .setQueryParameter(COMMAND, ROUTE_CONFIG_COMMAND)
                    .setQueryParameter(AGENCY, SF_MUNI_AGENCY)
                    .setQueryParameter(ROUTE, nextRoute.tag)
                    .setQueryParameter("terse", "true")
                    .get();
            
            routeListResponse.onRedeem(new Callback<Response>() {
    
                @Override
                public void invoke(Response response) throws Throwable {
                    String routeXml = response.getBody();
                    
                    RouteList routeList = JAXB.unmarshal(new StringReader(routeXml), RouteList.class);
                    allRoutes.addAll(routeList.route);
                    
                    populateRoutes(routeNames);
                }
            });
        }
    }

    public List<Pair<Route.Stop,Route>> getNearbyStops(double latitude, double longitude, double distance) {
        List<Pair<Route.Stop,Route>> stops = new ArrayList<Pair<Route.Stop,Route>>();
        for (Route route : allRoutes) {
            for (Route.Stop stop : route.stops) {
                double stopDistance = GeoUtil.distanceInKilometers(latitude, longitude, stop.lat, stop.lon);
                if (stopDistance < distance)
                    stops.add(Pair.of(stop, route));
            }
        }
        return stops;
    }

    public PredictionList getPredictionListsForRoutes(List<Pair<Route.Stop,Route>> stops) {
        WSRequestHolder predictionRequest = WS
                .url(NEXTBUS_PUBLIC_XML_FEED)
                .setQueryParameter(COMMAND, PREDICT_MULTIPLE_STOPS_COMMAND)
                .setQueryParameter(AGENCY, SF_MUNI_AGENCY);
        
        for (Pair<Route.Stop,Route> stop : stops) {
            predictionRequest.setQueryParameter(STOPS, stop.getRight().tag + "|" + stop.getLeft().tag);
        }
        
        Promise<Response> predictionResponse = predictionRequest.get();

        String predictionXml = predictionResponse.get(10000).getBody();
        
        PredictionList predictionList = JAXB.unmarshal(new StringReader(predictionXml), PredictionList.class);

        return predictionList;
    }

    @Override
    public StopsModel getStops(double latitude, double longitude) {
        List<Pair<Route.Stop,Route>> nearbyStops = getNearbyStops(latitude, longitude, NEARBY);
        PredictionList predictionList = getPredictionListsForRoutes(nearbyStops);
        
        StopsModel stopsModel = new StopsModel();
        
        // Create a map between routes and it's stops
        Map<Route,List<Route.Stop>> routeMap = new HashMap<Route,List<Route.Stop>>();
        for (Pair<Route.Stop,Route> stop : nearbyStops) {
            if (routeMap.get(stop.getRight()) == null) {
                routeMap.put(stop.getRight(), new ArrayList<Route.Stop>());
            }
            routeMap.get(stop.getRight()).add(stop.getLeft());
        }
        
        stopsModel.routes = new ArrayList<StopsModel.Route>();
        for (Route route : routeMap.keySet()) {
            StopsModel.Route modelRoute = new StopsModel.Route();
            modelRoute.routeName = route.title;
            
            modelRoute.stops = new ArrayList<StopsModel.Stop>();
            for (Route.Stop stop : routeMap.get(route)) {
                StopsModel.Stop modelStop = new StopsModel.Stop();
                modelStop.stopName = stop.title;
                modelStop.predictions = new ArrayList<Integer>();
                
                modelStop.predictions.addAll(getPredictionsForStop(predictionList,route,stop));
                
                modelRoute.stops.add(modelStop);
            }
            stopsModel.routes.add(modelRoute);
        }
                
        
        return stopsModel;
    }

    private List<Integer> getPredictionsForStop(PredictionList predictionList, Route route, Stop stop) {
        List<Integer> minuteList = new ArrayList<Integer>();
        
        if (predictionList.directions == null)
            return minuteList;
        
        for (Direction direction : predictionList.directions) {
            for (Predictions predictions : direction.predictions) {
                for (Prediction prediction : predictions.prediction) {
                    if (predictions.routeCode.equals(route.tag) &&
                            predictions.stopTitle.equals(stop.title)) {
                        minuteList.add(prediction.minutes);
                    }
                }
            }
        }
        
        Collections.sort(minuteList);
        
        return minuteList;
    }
    
}
