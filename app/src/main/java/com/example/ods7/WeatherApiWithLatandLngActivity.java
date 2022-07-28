package com.example.ods7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class WeatherApiWithLatandLngActivity extends AppCompatActivity {

    TextView info;
    String baseUrl,key,mainUrl;
    RequestQueue queue;
    ProgressDialog progressDialog;
    String cityUrl,CityStr;
    EditText enterCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_api_with_latand_lng);
        info=findViewById(R.id.text_info);
        enterCity=findViewById(R.id.cityEdt);
        queue= Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);




    }
    private  void cityMethod()
    {
        cityUrl="https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    }

    public void locApi(View view) {
        progressDialog.show();

        info.setText("");

        baseUrl=getResources().getString(R.string.weather_api);
        key=getResources().getString(R.string.weather_key);
        mainUrl=baseUrl+"lat=27.175278406338602&lon=78.04213146843183&appid="+key;

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, mainUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {


                    JSONObject jsonObject=response.getJSONObject("coord");
                    double lon= jsonObject.getDouble("lon");
                    double lat= jsonObject.getDouble("lat");

                    JSONArray jsonArray=response.getJSONArray("weather");
                    JSONObject weatherObj=jsonArray.getJSONObject(0);

                    int id=weatherObj.getInt("id");
                    String main=weatherObj.getString("main");
                    String description=weatherObj.getString("description");

                    long visibility=response.getLong("visibility");

                    JSONObject wind=response.getJSONObject("wind");
                    double speed=wind.getDouble("speed");
                    double gust=wind.getDouble("gust");

                    String name=response.getString("name");

                    info.setText(""+lat+"\n"+lon+"\n"+id+
                            "\n"+main+"\n"+description+"\n"+visibility+"\n"+speed+"\n"+gust+"\n"+name);

                    Log.d("WeatherValues\n",""+lat+"\n"+lon+"\n"+id+
                            "\n"+main+"\n"+description+"\n"+visibility);

                    Toast.makeText(WeatherApiWithLatandLngActivity.this, ""+
                            ""+lat+"\n"+lon+"\n"+id+
                            "\n"+main+"\n"+description+"\n"+visibility+"\n"+speed+"\n"+gust+"\n"+name, Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(WeatherApiWithLatandLngActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(WeatherApiWithLatandLngActivity.this, ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        })/*{

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        }*/;

        queue.add(jsonObjectRequest);

    }

    public void cityname(View view) {
        progressDialog.show();
        CityStr=enterCity.getText().toString();
        key=getResources().getString(R.string.weather_key);

        cityUrl="https://api.openweathermap.org/data/2.5/weather?q="+CityStr+"&appid="+key;

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, cityUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                try {


                    JSONObject jsonObject=response.getJSONObject("coord");
                    double lon= jsonObject.getDouble("lon");
                    double lat= jsonObject.getDouble("lat");

                    JSONArray jsonArray=response.getJSONArray("weather");
                    JSONObject weatherObj=jsonArray.getJSONObject(0);

                    int id=weatherObj.getInt("id");
                    String main=weatherObj.getString("main");
                    String description=weatherObj.getString("description");

                    long visibility=response.getLong("visibility");

                    JSONObject wind=response.getJSONObject("wind");
                    double speed=wind.getDouble("speed");
                  //  double gust=wind.getDouble("gust");

                    String name=response.getString("name");

                    info.setText(""+lat+"\n"+lon+"\n"+id+
                            "\n"+main+"\n"+description+"\n"+visibility+"\n"+speed+"\n"+name);

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(WeatherApiWithLatandLngActivity.this, "kk"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getApplicationContext(), "Time out or no connection  error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "AuthFailureError error", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "ServerError error", Toast.LENGTH_SHORT).show();

                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "NetworkError error", Toast.LENGTH_SHORT).show();

                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "ParseError error", Toast.LENGTH_SHORT).show();

                }

            }
        })/*{

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        }*/;

        queue.add(jsonObjectRequest);
    }
}