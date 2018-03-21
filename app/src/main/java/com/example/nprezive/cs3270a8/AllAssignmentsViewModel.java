package com.example.nprezive.cs3270a8;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
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
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nprezive on 3/19/18.
 */

public class AllAssignmentsViewModel extends ViewModel {
    private LiveData<List<Assignment>> assignmentList;

    public LiveData<List<Assignment>> getAllAssignments(String courseid) {
        if (assignmentList == null) {
            String rawJSON = "";
            try {
                URL url = new URL("https://weber.instructure.com/api/v1/courses"
                        + courseid + "/assignments");
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

            //parse JSON
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Assignment[] assignments = null;
            try {
                assignments = gson.fromJson(rawJSON, Assignment[].class);
            }
            catch (Exception e) {
                Log.d("test", "Failed to parse JSON to object");
            }

//            assignmentList = Arrays.asList(assignments);
        }

        return assignmentList;
    }
}
