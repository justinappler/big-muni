package nextbus.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Prediction {
    @XmlAttribute
    public Integer seconds;

    @XmlAttribute
    public Integer minutes;
    
    @XmlAttribute
    public Long epochTime;
    
    @XmlAttribute
    public Boolean isDeparture;
}
