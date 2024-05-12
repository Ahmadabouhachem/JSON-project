import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;


public class JacksonStreamingReadExample {

    public static void main(String[] args) throws JsonParseException, IOException {

        //create JsonParser object
        JsonParser jsonParser = new JsonFactory().createParser(new File("package.json"));

        //loop through the tokens
        Employee emp = new Employee();
        Address address = new Address();
        emp.setAddress(address);
        emp.setCities(new ArrayList<String>());
        emp.setProperties(new HashMap<String, String>());
        List<Long> phoneNums = new ArrayList<Long>();
        boolean insidePropertiesObj=false;

        parseJSON(jsonParser, emp, phoneNums, insidePropertiesObj);

        long[] nums = new long[phoneNums.size()];
        int index = 0;
        for(Long l :phoneNums){
            nums[index++] = l;
        }
        emp.setPhoneNumbers(nums);

        jsonParser.close();
        //print employee object
        System.out.println("Employee Object\n\n"+emp);
    }

    private static void parseJSON(JsonParser jsonParser, Employee emp,
                                  List<Long> phoneNums, boolean insidePropertiesObj) throws JsonParseException, IOException {

        //loop through the JsonTokens
        while(jsonParser.nextToken() != JsonToken.END_OBJECT){
            String var = jsonParser.getCurrentName();
            if("id".equals(var)){
                jsonParser.nextToken();
                emp.setId(jsonParser.getIntValue());
            }else if("name".equals(var)){
                jsonParser.nextToken();
                emp.setName(jsonParser.getText());
            }else if("permanent".equals(var)){
                jsonParser.nextToken();
                emp.setPermanent(jsonParser.getBooleanValue());
            }else if("address".equals(var)){
                jsonParser.nextToken();
                //nested object, recursive call
                parseJSON(jsonParser, emp, phoneNums, insidePropertiesObj);
            }else if("street".equals(var)){
                jsonParser.nextToken();
                emp.getAddress().setStreet(jsonParser.getText());
            }else if("city".equals(var)){
                jsonParser.nextToken();
                emp.getAddress().setCity(jsonParser.getText());
            }else if("zipcode".equals(var)){
                jsonParser.nextToken();
                emp.getAddress().setZipcode(jsonParser.getIntValue());
            }else if("phoneNumbers".equals(var)){
                jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    phoneNums.add(jsonParser.getLongValue());
                }
            }else if("role".equals(var)){
                jsonParser.nextToken();
                emp.setRole(jsonParser.getText());
            }else if("cities".equals(var)){
                jsonParser.nextToken();
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    emp.getCities().add(jsonParser.getText());
                }
            }else if("properties".equals(var)){
                jsonParser.nextToken();
                while(jsonParser.nextToken() != JsonToken.END_OBJECT){
                    String key = jsonParser.getCurrentName();
                    jsonParser.nextToken();
                    String value = jsonParser.getText();
                    emp.getProperties().put(key, value);
                }
            }
        }
    }

}