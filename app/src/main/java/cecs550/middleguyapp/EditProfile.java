package cecs550.middleguyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        loginBtn = findViewById(R.id.updateBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editProfile();
                editBio();
            }
        });




    }

    public void editProfile() {
        SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        String password = sh.getString("password","empty");

        TextView TextViewNewName = (TextView) findViewById(R.id.editTextNewName);
        String newName = TextViewNewName.getText().toString();

        String postUrl = "http://159.65.191.124:3000/authenticated/doubly/user/name_change";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", userName);
            postData.put("password", password);
            postData.put("new_name", newName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = getApplicationContext();

                //CharSequence text = "Signed up Successfully!";
                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("message");
                    final Toast toast = Toast.makeText(context, desc, duration);
                    //textViewUser.setText((desc));
                    toast.show();
                    if (desc.equals("User name updated!"))
                    {

                        openMainAct();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void editBio() {
        SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        String password = sh.getString("password","empty");

        TextView bio = (TextView) findViewById(R.id.editTextBio);
        String userBio = bio.getText().toString();

        String postUrl = "http://159.65.191.124:3000/authenticated/doubly/user/bio_change";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", userName);
            postData.put("password", password);
            postData.put("new_bio", userBio);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = getApplicationContext();

                //CharSequence text = "Signed up Successfully!";
                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("message");
                    final Toast toast = Toast.makeText(context, desc, duration);
                    //textViewUser.setText((desc));
                    toast.show();
                    if (desc.equals("Bio updated!"))
                    {

                        openMainAct();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }



    public void openMainAct() {
       int activity = 0;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("activity",activity);
        startActivity(intent);
        finish();
    }
}