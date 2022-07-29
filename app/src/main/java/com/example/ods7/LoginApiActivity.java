package com.example.ods7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginApiActivity extends AppCompatActivity {


    EditText em,pass;
    TextView response_text;

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String baseurl="https://www.androiddada.com/Clostitch/Code/Clo/Login";
    String emStr,passStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_api);

        em=findViewById(R.id.em_edt);
        pass=findViewById(R.id.pass_edt);
        response_text=findViewById(R.id.response_text);

        requestQueue= Volley.newRequestQueue(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginMethod();
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginApiActivity.this,SignupApiActivity.class));
            }
        });
    }

    private void loginMethod() {
        progressDialog.show();

        emStr=em.getText().toString();
        passStr=pass.getText().toString();

        HashMap<String,Object> map=new HashMap<>();
        map.put("Email",emStr);
        map.put("Mobile",emStr);
        map.put("Password",passStr);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, baseurl, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                response_text.setText(""+response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                response_text.setText(""+error.getLocalizedMessage());


            }
        })
                //Form Data Key,value using HashMap
        /*{
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        }*/
                ;

        requestQueue.add(jsonObjectRequest);
    }

}