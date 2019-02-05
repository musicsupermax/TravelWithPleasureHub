package com.kh021j.travelwithpleasurehub.propertyrent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class ImgurAPIService {

    private static String token;

    public static void getToken() {
        final String uri = "https://api.imgur.com/oauth2/token";

        Map<String, String> params = new HashMap<>();
        params.put("refresh_token", "d71cf4db9f8f2b140e4cf668dda84f7d02789026");
        params.put("client_id", "63a19ad5929af06");
        params.put("client_secret", "8341a1bc88b6f201f4f3851bf31696b21e370b83");
        params.put("grant_type", "refresh_token");

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, params, String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        try {
            map = mapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        token = (String) map.get("access_token");
    }

    public String uploadPictures(MultipartFile photo)
    {
        final String uri = "https://api.imgur.com/3/image";
        MultiValueMap<String, ByteArrayResource> body = new LinkedMultiValueMap<>();
        try {
            body.add("image", new ByteArrayResource(photo.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, ByteArrayResource>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(uri, request, String.class);

        String link = null;
        try {
            JSONObject obj = new JSONObject(result);
            JSONObject data = obj.getJSONObject("data");
            link = data.getString("link");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return link;
    }

}
