import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateJson {
    public static void main(String[] args) throws IOException {

        byte[] jsonData = Files.readAllBytes(Paths.get("package.json"));

        ObjectMapper objectMapper = new ObjectMapper();

        //create JsonNode
        JsonNode rootNode = objectMapper.readTree(jsonData);

        //update JSON data
        ((ObjectNode) rootNode).put("id", 10);

        ((ObjectNode) rootNode).put("name", "ahmad");

        //add new key value
        ((ObjectNode) rootNode).put("University", "IU Information");

        //add a new map object
        Map<String, Object> newAddress = new HashMap<>();
        newAddress.put("street", "Lotte-profohs-weg");
        newAddress.put("house number", 4);
        newAddress.put("stair case ", 3);
        newAddress.put("stair", 1);
        ((ObjectNode) rootNode).putPOJO("newAddress", newAddress);

        //add an arraylist with long type
        ArrayList<Long> phoneNumber = new ArrayList<Long>();
        phoneNumber.add(+436645107169L);
        phoneNumber.add(+43123123123L);
        ((ObjectNode) rootNode).putPOJO("phone number", phoneNumber);

        //add a long list
        Long[] phoneNumberV2 = new Long[]{436645107169L, 43123123123L};
        ((ObjectNode) rootNode).putPOJO("number version2", phoneNumberV2);

        //add a string list
        String[] description = new String[]{"ich werde bald umziehen", "ich suche mich eine gute Arbeit"};
        ((ObjectNode) rootNode).putPOJO("description", description);

        //add a object from a java class
        Address newClassAddress = new Address();
        newClassAddress.setCity("vienna");
        newClassAddress.setStreet("muhrengasse");
        newClassAddress.setZipcode(1100);

        ((ObjectNode) rootNode).putPOJO("newClassAddress", newClassAddress);

        //remove existing key
        ((ObjectNode) rootNode).remove("role");
        ((ObjectNode) rootNode).remove("properties");
        ((ObjectNode) rootNode).remove("permanent");
        ((ObjectNode) rootNode).remove("address");
        ((ObjectNode) rootNode).remove("phoneNumbers");
        ((ObjectNode) rootNode).remove("cities");
        ((ObjectNode) rootNode).remove("test");
        ((ObjectNode) rootNode).remove("Consoles");

        //creating the updated file
        objectMapper.writeValue(new File("updated_package.json"), rootNode);
    }
}
