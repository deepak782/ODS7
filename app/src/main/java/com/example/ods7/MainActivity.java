package com.example.ods7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String baseUrl;
    RequestQueue queue;
    ProgressDialog progressDialog;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.JsonList);

        queue= Volley.newRequestQueue(this);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        findViewById(R.id.placeholder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                baseUrl=getResources().getString(R.string.placeholder_api);
                loadData(baseUrl);
            }
        });
    }

    private void loadData(String baseUrl) {
        arrayList.clear();
        progressDialog.show();
        progressDialog.setMessage("Fetching Api Data!....");

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressDialog.dismiss();
               // Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                for(int i=0;i< response.length();i++)
                {

                    try {
                        JSONObject jsonObject= response.getJSONObject(i);
                        int userId=jsonObject.getInt("userId");
                        int id=jsonObject.getInt("id");
                        String title=jsonObject.getString("title");
                        String body=jsonObject.getString("body");
                        arrayList.add("UserID:- "+userId+"\n"+"ID:- "+id+"\n"+"Title:- "+title+"Body:- "+body);
                        arrayAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(MainActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);




    }
}