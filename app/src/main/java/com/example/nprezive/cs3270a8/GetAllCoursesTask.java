package com.example.nprezive.cs3270a8;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nprezive.cs3270a8.db.AppDatabase;
import com.example.nprezive.cs3270a8.db.Course;
import com.example.nprezive.cs3270a8.db.CourseDAO;
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
 * Created by nprezive on 3/19/18.
 */

public class GetAllCoursesTask extends AsyncTask<MainActivity, Integer, String> {

    MainActivity activity;
    public String rawJSON = "";

    @Override
    protected String doInBackground(MainActivity... mainActivities) {
        try {
            activity = mainActivities[0];
            URL url = new URL("https://weber.instructure.com/api/v1/courses");
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

        return rawJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Course[] courses = parseJson(s);
        CourseDAO courseDAO = AppDatabase.getInstance(activity).courseDAO();
        courseDAO.deleteAll();
        courseDAO.insertAll(courses);
    }

    private Course[] parseJson(String s) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Course[] courses = null;

        try {
            courses = gson.fromJson(s, Course[].class);
        }
        catch (Exception e) {
            Log.d("test", "Failed to parse JSON to object");
        }

        return courses;
    }
}
