package com.kh021j.travelwithpleasurehub.propertyrent.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class HEREMapsGeocoderAPIService {

    public List<Double> getCoordinatesByAddress(String address) {
        final String url = "https://geocoder.api.here.com/6.2/geocode.json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("searchtext", address.replaceAll(" ", "+"))
                .queryParam("app_code", "AR8OAZmJZPtl8mNegMW94w")
                .queryParam("app_id", "6J9fCy6htv1xIoObLj3n");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        List<Double> coordinates = new ArrayList<>();
        try {
            JSONObject body = new JSONObject(response.getBody());
            JSONObject responseObject = body.getJSONObject("Response");
            JSONArray viewArray = responseObject.getJSONArray("View");
            JSONObject firstObjectFromViewArray = viewArray.getJSONObject(0);
            JSONArray resultArray = firstObjectFromViewArray.getJSONArray("Result");
            JSONObject firstObjectFromResultArray = resultArray.getJSONObject(0);
            JSONObject locationObject = firstObjectFromResultArray.getJSONObject("Location");
            JSONArray navigationPositionArray = locationObject.getJSONArray("NavigationPosition");
            JSONObject firstObjectFromNavigationPositionArray = navigationPositionArray.getJSONObject(0);
            Double latitude = firstObjectFromNavigationPositionArray.getDouble("Latitude");
            Double longitude = firstObjectFromNavigationPositionArray.getDouble("Longitude");
            coordinates.add(latitude);
            coordinates.add(longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return coordinates;
    }

}
