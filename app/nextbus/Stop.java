package nextbus;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Stop {
    @JacksonXmlProperty(isAttribute=true)
    public String tag;

    @JacksonXmlProperty(isAttribute=true)
    public String title;
    
    @JacksonXmlProperty(isAttribute=true)
    public String shortTitle;
    
    @JacksonXmlProperty(isAttribute=true)
    public String lat;
    
    @JacksonXmlProperty(isAttribute=true)
    public String lon;
    
    @JacksonXmlProperty(isAttribute=true)
    public Integer stopId;
}
