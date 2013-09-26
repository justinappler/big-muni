package nextbus;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Predictions {
    @XmlAttribute
    public String stopTitle;

    @XmlAttribute
    public String routeCode;
    
    @XmlAttribute
    public String routeTitle;
    
    @XmlElement
    public List<Prediction> prediction;
}
