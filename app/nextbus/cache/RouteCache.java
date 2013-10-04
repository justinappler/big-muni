package nextbus.cache;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import nextbus.models.Route;

/**
 * Cache for routes retrieved from the NextBus API.
 * 
 * Will save all route data as XML to a 'route-cache' directory
 * and will attempt to load from that directory on request.
 * 
 * @author justinappler
 */
public class RouteCache {
    
    private static RouteCache instance;
    private static List<Route> routes;
    
    private static final String CACHE_DIR = "route-cache";

    static {
        getInstance();
    }
    
    private synchronized static RouteCache getInstance() {
        if (instance == null) {
            instance = new RouteCache();
        }
        
        return instance;
    }
    
    private RouteCache() {
        load();
    }
    
    /**
     * Populates a list of routes from XML in the route-cache
     * directory
     */
    private void load() {
        routes = new ArrayList<Route>();
        
        Path cacheDir = Paths.get(CACHE_DIR);
        if (Files.isReadable(cacheDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(cacheDir)) {
                for (Path routeFile : stream) {
                    Route route = JAXB.unmarshal(routeFile.toFile(), Route.class);
                    routes.add(route);
                }
            } catch (IOException | DirectoryIteratorException e) {
                e.printStackTrace();
            }
        }
        
        if (routes.size() < 1)
            routes = null;
    }

    /**
     * Caches a list of routes as XML files
     * @param routesToCache
     */
    public static void cache(List<Route> routesToCache) {
        getInstance()._cache(routesToCache);
        
    }

    private void _cache(List<Route> routesToCache) {
        routes = routesToCache;
        
        Path cacheDir = Paths.get(CACHE_DIR);
        
        try {
            Files.createDirectory(cacheDir);
        } catch (IOException e) {
            // Can't create a directory? No worries, this will be an in-memory cache only.
        }
        
        if (Files.exists(cacheDir) && Files.isWritable(cacheDir)) {
            for (Route route : routesToCache) {
                try {
                    Path file = Files.createFile(cacheDir.resolve(Paths.get(route.tag + ".xml")));
                    JAXB.marshal(route, file.toFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Get a list of all cached routes
     * @return all cached routes, or null if there are no routes cached
     */
    public static List<Route> getRoutes() {
        return getInstance()._getRoutes();
    }

    private List<Route> _getRoutes() {
        return routes;
    }
}
