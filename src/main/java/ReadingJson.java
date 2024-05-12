import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadingJson {

    public static void main(String[] args) throws IOException {

        //read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get("package.json"));

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        //converting json to Map
        Map<String, String> myMap = new HashMap<String, String>();

        myMap = objectMapper.readValue(jsonData, HashMap.class);
        System.out.println("Map is: " + myMap + "\n");

        //read JSON like DOM Parser
        JsonNode rootNode = objectMapper.readTree(jsonData);

        JsonNode idNode = rootNode.path("id");
        JsonNode name = rootNode.path("name");
        JsonNode permanent = rootNode.path("permanent");
        JsonNode phoneNosNode = rootNode.path("phoneNumbers");

        //reading a specifics value from object in json file
        Map<String, Object> map = objectMapper.convertValue(rootNode, Map.class);
        Map<String, Object> addressTest = (Map<String, Object>) map.get("address");

        String street = (String) addressTest.get("street");
        String city = (String) addressTest.get("city");
        int zipcode = (int) addressTest.get("zipcode");

        System.out.println("______________");
        System.out.println("street: "+ street);
        System.out.println("city: "+ city);
        System.out.println("zipcode: "+ zipcode);
        System.out.println("______________");


        Iterator<JsonNode> elements = phoneNosNode.elements();
        while (elements.hasNext()) {
            JsonNode phone = elements.next();
            System.out.println("Phone No = " + phone.asLong() + "\n");
        }

        JsonNode addressNosNode = rootNode.path("address");
        Iterator<JsonNode> elementaddress = addressNosNode.elements();
        String test = "";
        while (elementaddress.hasNext()) {
            JsonNode address = elementaddress.next();
            test += address.asText() + " ";
        }

        System.out.println("Id = " + idNode.asInt());
        System.out.println("Name = " + name.asText());
        System.out.println("Permanent = " + permanent.asBoolean());
        System.out.println("Address is: " + test);

    }
}