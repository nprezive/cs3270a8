package com.example.nprezive.cs3270a8;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nprezive.cs3270a8.models.Assignment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nprezive on 3/20/18.
 */

public class GetAssignmentsTask extends AsyncTask<String, Integer, Assignment[]> {

    public String rawJSON = "";

    @Override
    protected Assignment[] doInBackground(String... strings) {
        try {
            URL url = new URL("https://weber.instructure.com/api/v1/courses"
                                + strings[0] + "/assignments");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + Authorization.AUTH_TOKEN);
            conn.connect();

            int status = conn.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    rawJSON = br.readLine();
            }
        } catch (MalformedURLException e) {
            Log.d("test", "URL is malformed.");
        } catch (ProtocolException e) {
            Log.d("test", "Protocol exception");
        } catch (IOException e) {
            Log.d("test", "IO Exception");
        }

        return parseJson(rawJSON);
    }

    private Assignment[] parseJson(String s) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Assignment[] assignments = null;

        try {
            assignments = gson.fromJson(s, Assignment[].class);
        }
        catch (Exception e) {
            Log.d("test", "Failed to parse JSON to object");
        }

        return assignments;
    }
}
