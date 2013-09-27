package nextbus.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="stop")
@XmlAccessorType(XmlAccessType.FIELD)
public class Stop {
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