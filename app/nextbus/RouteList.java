package nextbus;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="body")
public class RouteList {

    @JacksonXmlElementWrapper(useWrapping=false)
    public List<Route> route;
}
