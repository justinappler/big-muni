package nextbus.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.JAXB;

import nextbus.NextBusService;
import nextbus.cache.RouteCache;
import nextbus.models.PredictionList;
import nextbus.models.Route;
import nextbus.models.RouteList;
import nextbus.models.Stop;

import org.apache.commons.lang3.tuple.Pair;

import play.libs.F.Callback;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.libs.WS.WSRequestHolder;
import util.GeoUtil;

/**
 * Implementation of the Nextbus Service
 * @author justinappler
 */
public class NextBusServiceImpl implements NextBusService {

    private static NextBusServiceImpl instance;
    
    private List<Route> allRoutes;
    
    private AtomicBoolean isLoaded;
        
    private static final String NEXTBUS_PUBLIC_XML_FEED = "http://webservices.nextbus.com/service/publicXMLFeed";
    
    private static final String COMMAND = "command";
    private static final String AGENCY = "a";
    private static final String ROUTE = "r";
    private static final String STOPS = "stops";
    
    private static final String ROUTE_LIST_COMMAND = "routeList";
    private static final String ROUTE_CONFIG_COMMAND = "routeConfig";
    private static final String PREDICT_MULTIPLE_STOPS_COMMAND = "predictionsForMultiStops";
    private static final String SF_MUNI_AGENCY = "sf-muni";
    
    static {
        getInstance();
    }
    
    public  synchronized static NextBusService getInstance() {
        if (instance == null) {
            instance = new NextBusServiceImpl();
        }
        
        return instance;
    }
    
    private NextBusServiceImpl() { 
        isLoaded = new AtomicBoolean(false);
        
        allRoutes = RouteCache.getRoutes();
        if (allRoutes == null) {
            allRoutes = new ArrayList<Route>();
            try {
                populateRouteList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            isLoaded.set(true);
        }
    }
    
    /**
     * Gets a list of all routes served and then, one at a 
     * time, gets the route configuration for each of the routes.
     * 
     * Caches all routes when finished as local XML files. This
     * method should ideally be run very infrequently as it 
     * makes many calls to the Nextbus API.
     * 
     * @throws Exception
     */
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
            isLoaded.set(true);
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

    @Override
    public List<Pair<Stop,Route>> getNearbyStops(double latitude, double longitude, double distance) {
        while (!isLoaded.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {}
        }
        
        List<Pair<Stop,Route>> stops = new ArrayList<Pair<Stop,Route>>();
        for (Route route : allRoutes) {
            for (Stop stop : route.stops) {
                double stopDistance = GeoUtil.distanceInKilometers(latitude, longitude, stop.lat, stop.lon);
                if (stopDistance < distance)
                    stops.add(Pair.of(stop, route));
            }
        }
        return stops;
    }

    @Override
    public PredictionList getPredictionListsForRoutes(List<Pair<Stop,Route>> stops) {
        WSRequestHolder predictionRequest = WS
                .url(NEXTBUS_PUBLIC_XML_FEED)
                .setQueryParameter(COMMAND, PREDICT_MULTIPLE_STOPS_COMMAND)
                .setQueryParameter(AGENCY, SF_MUNI_AGENCY);
        
        for (Pair<Stop,Route> stop : stops) {
            predictionRequest.setQueryParameter(STOPS, stop.getRight().tag + "|" + stop.getLeft().tag);
        }
        
        Promise<Response> predictionResponse = predictionRequest.get();
        String predictionXml = predictionResponse.get(10000).getBody();
        PredictionList predictionList = JAXB.unmarshal(new StringReader(predictionXml), PredictionList.class);

        return predictionList;
    }
}
