
package com.kh021j.travelwithpleasurehub.tickets.parser;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Connection {

    HttpURLConnection createConnection() throws IOException;

}
