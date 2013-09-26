package util;

public class GeoUtil {
    
    public static double distanceInKilometers(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371.0;
        double dLat = degreesToRadians(lat2-lat1);
        double dLon = degreesToRadians(lon2-lon1); 
        
        double a = 
          Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2)) * 
          Math.sin(dLon/2) * Math.sin(dLon/2); 
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        double d = earthRadius * c;
        
        return d;
    }

    public static double degreesToRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }
}