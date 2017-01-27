package br.com.baceiredo.agenda.model.service;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ur5l on 29/09/2016.
 */

public class WebClient {


    private static final String URL_SERVICE = "https://www.caelum.com.br/mobile";

    public String post(String json){

        HttpURLConnection connection = null;

        try {
            URL url = new URL(URL_SERVICE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            /* Post */
            connection.setDoOutput(true);

            /**
             * Request
             */
            PrintStream request = new PrintStream(connection.getOutputStream());
            request.println(json);
            connection.connect();

            /**
             * Response
             */
            Scanner response = new Scanner(connection.getInputStream());
            return response.next();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

}
