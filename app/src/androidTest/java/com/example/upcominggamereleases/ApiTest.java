package com.example.upcominggamereleases;



import android.util.Log;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.upcominggamereleases.Models.VideoGame;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ApiTest {

    private String TAG = "EAD2CA2 TESTING";
    private String SERVICE_URI = "https://ca2restfulservice.azurewebsites.net/orderbytitle";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void ApiCallTest() {
        try {
            RequestQueue queue = Volley.newRequestQueue(mActivityTestRule.getActivity());
            Log.d(TAG, "Making request");
            try {
                StringRequest strObjRequest = new StringRequest(Request.Method.GET, SERVICE_URI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Type listOfGames = new TypeToken<ArrayList<VideoGame>>() {
                                }.getType();
                                List<VideoGame> vg = new Gson().fromJson(response, listOfGames);
                                Log.d(TAG, "Displaying data" + vg.toString());
                                Assert.assertNotNull(vg);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error" + error.toString());
                            }
                        });
                queue.add(strObjRequest);
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        } catch (Exception e2) {
            Log.d(TAG, e2.toString());

        }
    }
}
