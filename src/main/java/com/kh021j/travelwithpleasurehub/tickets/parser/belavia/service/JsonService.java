
package com.kh021j.travelwithpleasurehub.tickets.parser.belavia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh021j.travelwithpleasurehub.tickets.parser.belavia.BelaviaConnection;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class JsonService {

    private  HttpURLConnection connection;
     {
        try {
            connection = new BelaviaConnection().createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendJsonRequest(String jsonQuery) throws IOException {
        OutputStream os = connection.getOutputStream();
        os.write(jsonQuery.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    public String getJsonResponse(String jsonQuery) throws IOException {
        sendJsonRequest(jsonQuery);
        BufferedReader bufferedReader = new BufferedReader
                (new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String jsonString;
        while ((jsonString = bufferedReader.readLine()) != null) {
            jsonBuilder.append(jsonString);
        }
        connection.disconnect();
        bufferedReader.close();
        return new String(jsonBuilder);
    }

    public String getMinPriceFromResponse(String json) throws IOException {
        JsonNode itineraries = getNodeFromMainKey("itineraries", json);
        return itineraries.get(0).path("brands").get(0).get("total").toString();
    }

    private JsonNode getNodeFromMainKey(String mainKey, String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);
        return rootNode.path(mainKey);
    }

    private String getInfoFromItineraries(String key, String json) throws IOException {
        JsonNode itineraries = new JsonService().
                getNodeFromMainKey("itineraries", json);
        String departureDateTime = itineraries.get(0).
                get(key).toString().replace("\"", "");
        return departureDateTime.replace("T", " ");

    }

    public String getDepartureDateTime(String json) throws IOException {
        return getInfoFromItineraries("departureDateTime", json);
    }

    public String getArrivalDateTime(String json) throws IOException {
        return getInfoFromItineraries("arrivalDateTime", json);
    }

    public String getDuration(String json) throws IOException {
        return getInfoFromItineraries("duration", json);
    }
}
