package com.example.upcominggamereleases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.upcominggamereleases.Models.VideoGame;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.util.Log;
import com.google.gson.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.reflect.TypeToken;


// in build.gradle for Module:app
// implementation 'com.android.volley:volley:1.1.1'
// implementation 'com.google.code.gson:gson:2.8.5'

public class MainActivity extends AppCompatActivity {

    // uri of RESTful service on Azure, note: https, cleartext support disabled by default
    private String SERVICE_URI = "https://ca2restfulservice.azurewebsites.net/orderbytitle";
    private String SERVICE_SEARCH_URI = "https://ca2restfulservice.azurewebsites.net/searchbytitle/";
    private String SORT_BY_DATE = "https://ca2restfulservice.azurewebsites.net/OrderByDate";
    private String TAG = "EAD2CA2 APP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callService(view);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void callService(View v) {
        final TextView outputTextView = (TextView) findViewById(R.id.outputTextView);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(TAG, "Making request");
            try {
                if(searchView.getQuery().toString().isEmpty()){
                    StringRequest strObjRequest = new StringRequest(Request.Method.GET, SERVICE_URI,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //clear text
                                    outputTextView.setText("");
                                    Type listOfGames = new TypeToken<ArrayList<VideoGame>>() {
                                    }.getType();
                                    List<VideoGame> vg = new Gson().fromJson(response, listOfGames);
                                    int count = 0;
                                    for (int i = 0; i < vg.size(); i++) {
                                        int ageRating = Integer.parseInt(vg.get(i).ageRating);
                                        int filter = Integer.parseInt(v.getTag().toString());
                                        String formattedDate = vg.get(i).releaseDate.substring(0, 10);
                                        if (ageRating <= filter ) {
                                            outputTextView.setText(outputTextView.getText() + getString(R.string.title) + ":    " + vg.get(i).title + "\n" + getString(R.string.age_rating) + ":    " + vg.get(i).ageRating + "+" +  "\n" + getString(R.string.release_date) + ":    " + formattedDate + "\n\n");
                                            count++;
                                        } else if (filter == 0) {
                                            outputTextView.setText(outputTextView.getText() + getString(R.string.title) + ":    " + vg.get(i).title + "\n" + getString(R.string.age_rating) + ":    " + vg.get(i).ageRating + "+" +  "\n" + getString(R.string.release_date) + ":    " + formattedDate + "\n\n");
                                            count++;
                                        }
                                    }
                                    if (count == 0) {
                                        outputTextView.setText(getString(R.string.no_games));
                                    }
                                    Log.d(TAG, "Displaying data" + vg.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    outputTextView.setText(error.toString());
                                    Log.d(TAG, "Error" + error.toString());
                                }
                            });
                    queue.add(strObjRequest);
                }
                else{
                    StringRequest strObjRequest = new StringRequest(Request.Method.GET, SERVICE_SEARCH_URI+searchView.getQuery().toString(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //clear text
                                    outputTextView.setText("");
                                    Type listOfGames = new TypeToken<ArrayList<VideoGame>>() {
                                    }.getType();
                                    List<VideoGame> vg = new Gson().fromJson(response, listOfGames);
                                    int count = 0;
                                    for (int i = 0; i < vg.size(); i++) {
                                        String ageRating = vg.get(i).ageRating;
                                        String filter = v.getTag().toString();
                                        String formattedDate = vg.get(i).releaseDate.substring(0, 10);
                                        if (ageRating.equals(filter)) {
                                            outputTextView.setText(outputTextView.getText() + getString(R.string.title) + ":    " + vg.get(i).title + "\n" + getString(R.string.age_rating) + ":    " + vg.get(i).ageRating + "+" +  "\n" + getString(R.string.release_date) + ":    " + formattedDate + "\n\n");
                                            count++;
                                        } else if (filter.equals("all")) {
                                            outputTextView.setText(outputTextView.getText() + getString(R.string.title) + ":    " + vg.get(i).title + "\n" + getString(R.string.age_rating) + ":    " + vg.get(i).ageRating + "+" +  "\n" + getString(R.string.release_date) + ":    " + formattedDate + "\n\n");
                                            count++;
                                        }
                                    }
                                    if (count == 0) {
                                        outputTextView.setText(getString(R.string.no_games));
                                    }
                                    Log.d(TAG, "Displaying data" + vg.toString());
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    outputTextView.setText(error.toString());
                                    Log.d(TAG, "Error" + error.toString());
                                }
                            });
                    queue.add(strObjRequest);
                }
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
                outputTextView.setText(e1.toString());
            }
        } catch (Exception e2) {
            Log.d(TAG, e2.toString());
            outputTextView.setText(e2.toString());
        }
    }

    public void orderByDate(View v) {
        final TextView outputTextView = (TextView) findViewById(R.id.outputTextView);

        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(TAG, "Making request");
            try {
                StringRequest strObjRequest = new StringRequest(Request.Method.GET, SORT_BY_DATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //clear text
                                outputTextView.setText("");
                                Type listOfGames = new TypeToken<ArrayList<VideoGame>>() {
                                }.getType();
                                List<VideoGame> vg = new Gson().fromJson(response, listOfGames);
                                for (int i = 0; i < vg.size(); i++) {
                                    outputTextView.setText(outputTextView.getText() + getString(R.string.title) + ":    " + vg.get(i).title + "\n" + getString(R.string.age_rating) + ":    " + vg.get(i).ageRating + "+" +  "\n" + getString(R.string.release_date) + ":    " + vg.get(i).releaseDate + "\n\n");
                                }
                                Log.d(TAG, "Displaying data" + vg.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                outputTextView.setText(error.toString());
                                Log.d(TAG, "Error" + error.toString());
                            }
                        });
                queue.add(strObjRequest);
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
                outputTextView.setText(e1.toString());
            }
        } catch (Exception e2) {
            Log.d(TAG, e2.toString());
            outputTextView.setText(e2.toString());
        }
    }
}
