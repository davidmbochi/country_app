package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button addButton, updateButton;
    EditText editText;
    ArrayAdapter<String> arrayAdapter;
    List<String> countries = new ArrayList<>();
    String country;
    int countryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.country_list_view);
        addButton = findViewById(R.id.add_btn);
        updateButton =findViewById(R.id.delete_btn);
        editText = findViewById(R.id.editTextTextCountryName);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String findAllUrl = "http://10.0.2.2:8080/country/all";

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,countries);
        listView.setAdapter(arrayAdapter);

        JsonArrayRequest findAllRequest = new JsonArrayRequest(Request.Method.GET, findAllUrl,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i =0; i< response.length(); i++){
                        countries.add(response.getJSONObject(i).getString("countryName"));
                    }
                    arrayAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(MainActivity.this,"Something wrong",Toast.LENGTH_SHORT).show());
        queue.add(findAllRequest);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addCountryUrl = "http://10.0.2.2:8080/country/add";
                JSONObject object = new JSONObject();
                try {
                    object.put("countryName",editText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest addCountryRequest = new JsonObjectRequest(Request.Method.POST, addCountryUrl, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            countries.add(response.getString("countryName"));
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(addCountryRequest);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 country = adapterView.getItemAtPosition(i).toString()+" has been selected";
                 countryPosition = i+1;
                 Toast.makeText(MainActivity.this,country+" number "+countryPosition,Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateCountryUrl = "http://10.0.2.2:8080/country/update/"+countryPosition;
                JSONObject object = new JSONObject();
                try {
                    object.put("countryName",editText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest updateCountryRequest = new JsonObjectRequest(Request.Method.PUT, updateCountryUrl, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                          countries.set(countryPosition - 1,response.getString("countryName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                queue.add(updateCountryRequest);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = i+1;
                String  deleteCountryUrl = "http://10.0.2.2:8080/country/delete/"+id;

                JsonObjectRequest deleteCountryRequest = new JsonObjectRequest(Request.Method.GET, deleteCountryUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        countries.remove(i);

                        country = adapterView.getItemAtPosition(id).toString()+" has been deleted";
                        Toast.makeText(MainActivity.this,country+" number "+id,Toast.LENGTH_SHORT).show();


                        arrayAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(deleteCountryRequest);
                return true;
            }
        });



    }
}