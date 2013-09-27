package nextbus.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PredictionList {
    
    @XmlElement(name="predictions")
    public List<Predictions> allPredictions;
    
    /**
     * Gets arrival predictions for a given stop
     * @param route
     * @param stop
     * @return a list of predictions for a stop, in minutes
     */
    public List<Integer> getPredictionsForStop(Stop stop) {
        List<Integer> minuteList = new ArrayList<Integer>();
        
        if (allPredictions == null)
            return minuteList;
        
        for (Predictions predictions : allPredictions) {
            if (predictions.direction != null && predictions.direction.prediction != null) {
                for (Prediction prediction : predictions.direction.prediction) {
                        if (predictions.stopTag.equals(stop.tag)) {
                            minuteList.add(prediction.minutes);
                        }
                }
            }
        }
        
        Collections.sort(minuteList);
        
        return minuteList;
    }
}
