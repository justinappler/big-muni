package nextbus;

import java.io.IOException;
import java.util.List;

import models.StopsModel;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XMLParseTester {
    
    public static final String testXml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?> \n" + 
            "<body copyright=\"All data copyright San Francisco Muni 2013.\">\n" + 
            "<route tag=\"J\" title=\"J-Church\" color=\"cc6600\" oppositeColor=\"000000\" latMin=\"37.7208999\" latMax=\"37.7932299\" lonMin=\"-122.4468699\" lonMax=\"-122.3964\">\n" + 
            "<stop tag=\"7217\" title=\"Embarcadero Station OB\" lat=\"37.7932299\" lon=\"-122.39654\" stopId=\"17217\"/>\n" + 
            "<stop tag=\"6994\" title=\"Montgomery Station Outbound\" lat=\"37.78879\" lon=\"-122.4021299\" stopId=\"16994\"/>\n" + 
            "<direction tag=\"J__IB1\" title=\"Inbound to Downtown\" name=\"Inbound\" useForUI=\"true\">\n" + 
            "  <stop tag=\"5448\" />\n" + 
            "  <stop tag=\"6275\" />\n" + 
            "  <stop tag=\"6288\" />\n" + 
            "  <stop tag=\"6284\" />\n" + 
            "  <stop tag=\"6277\" />\n" + 
            "  <stop tag=\"3537\" />\n" + 
            "  <stop tag=\"4000\" />\n" + 
            "  <stop tag=\"3999\" />\n" + 
            "  <stop tag=\"3998\" />\n" + 
            "  <stop tag=\"4003\" />\n" + 
            "  <stop tag=\"3996\" />\n" + 
            "  <stop tag=\"3994\" />\n" + 
            "  <stop tag=\"6217\" />\n" + 
            "  <stop tag=\"6222\" />\n" + 
            "  <stop tag=\"6215\" />\n" + 
            "  <stop tag=\"6213\" />\n" + 
            "  <stop tag=\"3985\" />\n" + 
            "  <stop tag=\"7073\" />\n" + 
            "  <stop tag=\"4006\" />\n" + 
            "  <stop tag=\"5419\" />\n" + 
            "  <stop tag=\"5727\" />\n" + 
            "  <stop tag=\"5417\" />\n" + 
            "  <stop tag=\"5731\" />\n" + 
            "  <stop tag=\"6992\" />\n" + 
            "</direction>\n" + 
           "<direction tag=\"J__IBMTCH\" title=\"Inbound to Church &amp; Duboce\" name=\"Inbound\" useForUI=\"true\">\n" + 
            "  <stop tag=\"5448\" />\n" + 
            "  <stop tag=\"6275\" />\n" + 
            "  <stop tag=\"6288\" />\n" + 
            "  <stop tag=\"6284\" />\n" + 
            "  <stop tag=\"4787\" />\n" + 
            "  <stop tag=\"6277\" />\n" + 
            "  <stop tag=\"3537\" />\n" + 
            "  <stop tag=\"4000\" />\n" + 
            "  <stop tag=\"3999\" />\n" + 
            "  <stop tag=\"3998\" />\n" + 
            "  <stop tag=\"4003\" />\n" + 
            "  <stop tag=\"3996\" />\n" + 
            "  <stop tag=\"3994\" />\n" + 
            "  <stop tag=\"6217\" />\n" + 
            "  <stop tag=\"6222\" />\n" + 
            "  <stop tag=\"6215\" />\n" + 
            "  <stop tag=\"6213\" />\n" + 
            "  <stop tag=\"3985\" />\n" + 
            "  <stop tag=\"7073\" />\n" + 
            "  <stop tag=\"4006\" />\n" + 
            "</direction>\n" + 
            "<direction tag=\"J__OB1\" title=\"Outbound to Balboa Park Station\" name=\"Outbound\" useForUI=\"true\">\n" + 
            "  <stop tag=\"7217\" />\n" + 
            "  <stop tag=\"6994\" />\n" + 
            "  <stop tag=\"6995\" />\n" + 
            "  <stop tag=\"6997\" />\n" + 
            "  <stop tag=\"6996\" />\n" + 
            "  <stop tag=\"7316\" />\n" + 
            "  <stop tag=\"3984\" />\n" + 
            "  <stop tag=\"3987\" />\n" + 
            "  <stop tag=\"6214\" />\n" + 
            "  <stop tag=\"6221\" />\n" + 
            "  <stop tag=\"6216\" />\n" + 
            "  <stop tag=\"6218\" />\n" + 
            "  <stop tag=\"3995\" />\n" + 
            "  <stop tag=\"4002\" />\n" + 
            "  <stop tag=\"3997\" />\n" + 
            "  <stop tag=\"4004\" />\n" + 
            "  <stop tag=\"3538\" />\n" + 
            "  <stop tag=\"6280\" />\n" + 
            "  <stop tag=\"4788\" />\n" + 
            "  <stop tag=\"6285\" />\n" + 
            "  <stop tag=\"6287\" />\n" + 
            "  <stop tag=\"6274\" />\n" + 
            "  <stop tag=\"7155\" />\n" + 
            "  <stop tag=\"35448\" />\n" + 
            "</direction>\n" + 
            "</route>\n" + 
            "</body>\n" + 
            "";

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {        
        NextBusService service = new NextBusServiceImpl();

        StopsModel stops = service.getStops(37.792030, -122.416739);
        
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(stops));
    }

}
