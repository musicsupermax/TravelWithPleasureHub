package com.kh021j.travelwithpleasurehub.tickets.parser.belavia;

import com.kh021j.travelwithpleasurehub.tickets.parser.Connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BelaviaConnection implements Connection {
    private final String QUERY_URL = "https://ibe.belavia.by/api/flights/outbound";
    private HttpURLConnection connection;

    @Override
    public HttpURLConnection createConnection() throws IOException {
        URL url = new URL(QUERY_URL);
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        return connection;
    }
}
