package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

     //@Override
     String url, city;
     EditText etCity;
     Button btCheck;
     TextView tvDisplay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.etCity);
        btCheck = findViewById(R.id.btCheck);
        tvDisplay = findViewById(R.id.tvDisplay);

        etCity.setTextColor(Color.WHITE);
        etCity.setHintTextColor(Color.GRAY);

        tvDisplay.setTypeface(null, Typeface.BOLD_ITALIC);
        tvDisplay.setTextColor(Color.WHITE);

        btCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

    }
    private void check(){

        city =etCity.getText().toString();
        url ="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=91180038acc501b7239f26d2d9b1dc23&units=metric";

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest j = new JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                        try{
                            JSONObject obj = response.getJSONObject("main");
                            JSONArray obj1 = response.getJSONArray("weather");
                            JSONObject obj2 = obj1.getJSONObject(0);
                            JSONObject obj3 = response.getJSONObject("sys");
                            //Log.e("Response", response.toString());
                            //Log.e("Response", description);
                            String country = obj3.getString("country");
                            String city = response.getString("name");
                            String description = obj2.getString("description");
                            String temp = String.valueOf(obj.getDouble("temp"));
                            String min = String.valueOf(obj.getDouble("temp_min"));
                            String max = String.valueOf(obj.getDouble("temp_max"));
                            String humidity = String.valueOf(obj.getDouble("humidity"));
                            String location = city + ", " + country;
                            String pressure = String.valueOf(obj.getDouble("pressure"));
                            String display = location+"\n\nTemperature - "+temp+"°C\n\nMinimum Temperature - "+min+"°C\n\nMaximum Temperature - "+max+"°C\n\nHumidity - "+humidity+"%\n\nPressure -  "+pressure+" hPa\n\nDescription -"+description;
                            tvDisplay.setText(display);

                        }
                        catch(JSONException e){
                            Log.e("Error Response", response.toString());
                        }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        rq.add(j);
    }
}
