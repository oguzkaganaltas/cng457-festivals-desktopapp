package com.mustafaoguz.cng457festivalsdesktopapp;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;


public class StatisticsConcert implements Runnable {

    @FXML
    private ListView concertListView;

    StatisticsConcert(ListView concertListView){
        this.concertListView = concertListView;
    }

    @Override
    public void run() {
        concertListView.getItems().clear();
        StringBuilder responseConcert = new StringBuilder();
        HttpURLConnection connectionConcert = null;
        try {
            connectionConcert = (HttpURLConnection)new URL("http://localhost:8080/longestconcerts").openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connectionConcert.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        int responseCodeFestival = 0;
        try {
            responseCodeFestival = connectionConcert.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responseCodeFestival == 200){
            Scanner scanner = null;
            try {
                scanner = new Scanner(connectionConcert.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(scanner.hasNextLine()){

                responseConcert.append(scanner.nextLine());
            }
            scanner.close();
        }
        JSONParser parserConcert = new JSONParser();
        JSONArray arrayConcert = null;
        try {
            arrayConcert = (JSONArray) parserConcert.parse(responseConcert.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Object o : arrayConcert) {
            try {
                JSONObject temp = (JSONObject) o;
                concertListView.getItems().add(temp.get("eventId") + "-" + temp.get("name"));
            } catch (Exception e) {
                StringBuilder responseCatch = new StringBuilder();
                HttpURLConnection connectionCatch = null;
                try {
                    connectionCatch = (HttpURLConnection)new URL("http://localhost:8080/getconcert/"+o.toString()).openConnection();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    connectionCatch.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    ex.printStackTrace();
                }
                int responseCodeCatch = 0;
                try {
                    responseCodeCatch = connectionCatch.getResponseCode();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(responseCodeCatch == 200){
                    Scanner scannerCatch = null;
                    try {
                        scannerCatch = new Scanner(connectionCatch.getInputStream());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    while(scannerCatch.hasNextLine()){
                        responseCatch.append(scannerCatch.nextLine());
                    }
                    scannerCatch.close();
                }

                JSONParser parserCatch = new JSONParser();
                JSONObject objectCatch = null;
                try {
                    objectCatch = (JSONObject) parserCatch.parse(responseCatch.toString());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                concertListView.getItems().add(objectCatch.get("eventId") + "-" + objectCatch.get("name"));
            }
        }
    }
}
