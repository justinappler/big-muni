package nextbus.models;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteList {

    @XmlElement
    public List<Route> route;
}
