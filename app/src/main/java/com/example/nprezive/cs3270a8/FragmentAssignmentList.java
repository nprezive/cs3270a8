package com.example.nprezive.cs3270a8;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nprezive.cs3270a8.models.Assignment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAssignmentList extends Fragment {

    private int cid;
    private String id, name, courseCode, startAt, endAt;

    public FragmentAssignmentList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //populate fields with stuff from the bundle
        Bundle bundle = this.getArguments();
        cid = bundle.getInt("cid");
        id = bundle.getString("id");
        name = bundle.getString("name");
        courseCode = bundle.getString("courseCode");
        startAt = bundle.getString("startAt");
        endAt = bundle.getString("endAt");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment_list, container, false);

        // Recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.assignmentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final AssignmentListRecyclerAdapter adapter =
                new AssignmentListRecyclerAdapter(new ArrayList<Assignment>());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        // api call to get assignments and load into adapter
        new Thread(new Runnable() {
            @Override
            public void run() {
                String rawJSON = "";
                try {
                    URL url = new URL("https://weber.instructure.com/api/v1/courses"
                            + id + "/assignments");
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
                    Log.d("test", rawJSON);
                    assignments = gson.fromJson(rawJSON, Assignment[].class);
                    Log.d("test", assignments[0].toString());
                }
                catch (Exception e) {
                    Log.d("test", "Failed to parse JSON to assignment");
                }

                adapter.addItems(new ArrayList<Assignment>(Arrays.asList(assignments)));
            }
        }).start();

        return view;
    }

}
