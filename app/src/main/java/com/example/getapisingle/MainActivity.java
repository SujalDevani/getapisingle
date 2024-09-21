package com.example.getapisingle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter3;
    List<Model> Products1;
    Button btnnext1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up button and RecyclerView
        btnnext1 = findViewById(R.id.btnnext1);
        btnnext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Products1 = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter3 = new Adapter(Products1, this);
        recyclerView.setAdapter(adapter3);

        // Fetch data from API
        getjsonData();
    }

    private void getjsonData() {
        String URL = "https://reqres.in/api/users/2";  // Single user API endpoint
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", "onResponse: " + response);
                try {
                    // Handle the response for a single user object
                    if (response.has("data")) {
                        JSONObject product = response.getJSONObject("data");

                        // Create a new Model object
                        Model v = new Model();
                        v.setId("ID: " + product.getString("id"));
                        v.setName("Name: " +  product.getString("first_name") + " " + product.getString("last_name"));                        v.setYear("Last Name: " + product.getString("last_name"));
                        v.setColor("Email: " + product.optString("email", "N/A"));
                        v.setAvatarUrl(product.getString("avatar"));

                        // Add the model to the list
                        Products1.add(v);

                        // Notify the adapter that data has changed
                        adapter3.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
            }
        });
        requestQueue.add(objectRequest);
    }
}
