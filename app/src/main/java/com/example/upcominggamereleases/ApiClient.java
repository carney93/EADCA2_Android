package com.example.upcominggamereleases;

import com.example.upcominggamereleases.Models.VideoGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    public String baseURL = "https://ca2restfulservice.azurewebsites.net/";

        public List<VideoGame> listAll() {
        String res="";
        try {
            //API GET CALL
            URL urlForGetRequest = new URL(baseURL+"orderbytitle");
            String readLine = null;
            HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            res = response.toString();

        } catch (IOException e) {

        }
        Type listOfGames = new TypeToken<ArrayList<VideoGame>>(){}.getType();
        Gson gson = new GsonBuilder().create();
        List<VideoGame> vg = gson.fromJson(res, listOfGames);
        return vg;
    }
}