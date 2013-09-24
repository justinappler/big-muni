package nextbus;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Route {

    @JacksonXmlProperty(isAttribute=true)
    public String tag;
    
    @JacksonXmlProperty(isAttribute=true)
    public String title;

    @JacksonXmlProperty(isAttribute=true)
    public String color;
    
    @JacksonXmlProperty(isAttribute=true)
    public String oppositeColor;
    
    @JacksonXmlProperty(isAttribute=true)
    public String shortTitle;

    @JacksonXmlProperty(isAttribute=true)
    public String latMin;

    @JacksonXmlProperty(isAttribute=true)
    public String latMax;

    @JacksonXmlProperty(isAttribute=true)
    public String lonMin;

    @JacksonXmlProperty(isAttribute=true)
    public String lonMax;
    
    @JacksonXmlElementWrapper(useWrapping=false)
    public List<Stop> stop;
    
    @JacksonXmlElementWrapper(useWrapping=false)
    public List<Direction> direction;
    
    public static class Direction {
        @JacksonXmlProperty(isAttribute=true)
        public String tag;
        
        @JacksonXmlProperty(isAttribute=true)
        public String title;

        @JacksonXmlProperty(isAttribute=true)
        public String name;
        
        @JacksonXmlProperty(isAttribute=true)
        public Boolean useForUI;
        
        @JacksonXmlElementWrapper(useWrapping=false)
        public List<Stop> stop;
    }
}
