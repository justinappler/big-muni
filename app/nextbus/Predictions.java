package nextbus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Predictions {
    @XmlAttribute
    public String stopTitle;

    @XmlAttribute
    public String stopTag;
    
    @XmlAttribute
    public String routeTag;
    
    @XmlAttribute
    public String routeTitle;
    
    @XmlElement
    public Direction direction;
}
