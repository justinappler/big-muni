package nextbus;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Route {

    @XmlAttribute
    public String tag;
    
    @XmlAttribute
    public String title;

    @XmlAttribute
    public String color;
    
    @XmlAttribute
    public String oppositeColor;
    
    @XmlAttribute
    public String shortTitle;

    @XmlAttribute
    public String latMin;

    @XmlAttribute
    public String latMax;

    @XmlAttribute
    public String lonMin;

    @XmlAttribute
    public String lonMax;
    
    @XmlElement(name="direction")
    public List<Route.Direction> directions;
    
    @XmlElement(name="stop")
    public List<Route.Stop> stops;
    
    @XmlRootElement(name="direction")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Direction {
        @XmlAttribute
        public String tag;
        
        @XmlAttribute
        public String title;

        @XmlAttribute
        public String name;
        
        @XmlAttribute
        public Boolean useForUI;
        
        @XmlElement
        public List<Stop> stop;
    }
    
    @XmlRootElement(name="stop")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Stop {
        @XmlAttribute
        public String tag;

        @XmlAttribute
        public String title;
        
        @XmlAttribute
        public String shortTitle;
        
        @XmlAttribute
        public double lat;
        
        @XmlAttribute
        public double lon;
        
        @XmlAttribute
        public Integer stopId;
    }
    

    @Override
    public int hashCode() {
        return tag.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        
        if (!(obj instanceof Route))
            return false;
        
        return tag.equals(((Route) obj).tag);
    }
}
