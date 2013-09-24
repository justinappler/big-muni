package nextbus;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLParseTester {
    
    public static final String testXml = "<body>\n" + 
            "      <route tag=\"N\" title=\"N - Judah\" color=\"003399\" oppositeColor=\"ffffff\"\n" + 
            "latMin=\"37.7601699\" latMax=\"37.7932299\" lonMin=\"-122.5092\" lonMax=\"-122.38798\">\n" + 
            "        <stop tag=\"KINGd4S0\" title=\"King St and 4th St\" shortTitle=\"King &amp; 4th\"\n" + 
            "         lat=\"37.776036\" lon=\"-122.394355\" stopId=\"1\"/>\n" + 
            "        <stop tag=\"KINGd2S0\" title=\"King St and 2nd St\" shortTitle=\"King &amp; 2nd\"\n" + 
            "         lat=\"37.7796152\" lon=\"-122.3898067\" stopId=\"2\"/>\n" + 
            "        <stop tag=\"EMBRBRAN\" title=\"Embarcadero and Brannan St\"\n" + 
            "         shortTitle=\"Embarcadero &amp; Brannan\" lat=\"37.7844455\" lon=\"-122.3880081\"\n" + 
            "         stopId=\"3\"/>\n" + 
            "        <stop tag=\"EMBRFOLS\" title=\"Embarcadero and Folsom St\"\n" + 
            "         shortTitle=\"Embarcadero &amp; Folsom\" lat=\"37.7905742\" lon=\"-122.3896326\"\n" + 
            "         stopId=\"4\"/>\n" + 
            "        <direction tag=\"out\" title=\"Outbound to La Playa\" name=\"Outbound\"\n" + 
            "useForUI=\"true\">\n" + 
            "          <stop tag=\"KINGd4S0\"/>\n" + 
            "          <stop tag=\"KINGd2S0\"/>\n" + 
            "          <stop tag=\"EMBRBRAN\"/>\n" + 
            "          <stop tag=\"EMBRFOLS\"/>\n" + 
            "          <stop tag=\"CVCENTF\"/>\n" + 
            "        </direction>\n" + 
            "        <direction tag=\"in\" title=\"Inbound to Caltrain\" name=\"Inbound\"\n" + 
            "useForUI=\"true\">\n" + 
            "          <stop tag=\"CVCENTF\"/>\n" + 
            "          <stop tag=\"EMBRFOLS\"/>\n" + 
            "          <stop tag=\"EMBRBRAN\"/>\n" + 
            "          <stop tag=\"KINGd2S0\"/>\n" + 
            "          <stop tag=\"KINGd4S0\"/>\n" + 
            "        </direction>\n" + 
            "        <direction tag=\"in_short\" title=\"Short Run\" name=\"Inbound\"\n" + 
            "useForUI=\"false\">\n" + 
            "          <stop tag=\"CVCENTF\"/>\n" + 
            "          <stop tag=\"EMBRFOLS\"/>\n" + 
            "          <stop tag=\"EMBRBRAN\"/>\n" + 
            "        </direction></route></body>";

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        List<Route> value = xmlMapper.readValue(testXml, new TypeReference<List<Route>>() {});
        System.out.println(value);
    }

}
