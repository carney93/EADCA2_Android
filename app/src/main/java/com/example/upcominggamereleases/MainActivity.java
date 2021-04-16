package com.example.upcominggamereleases;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAllGames(View v) {

        ApiCaller apiCaller = new ApiCaller();
        //change this
            try{
                TableLayout tableLayout = findViewById(R.id.table1);
                TextView text = new TextView(v.getContext());
                text.setText(apiCaller.listAll());
            }catch (Exception e){

            }
    }
}