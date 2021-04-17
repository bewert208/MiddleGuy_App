package cecs550.middleguyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public Button loginBtn;
    public Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        loginBtn = findViewById(R.id.loginButton);
        signUpBtn = findViewById(R.id.signUpButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyPostLogin();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyPostSign();
            }
        });

    }

    public void openLogin_Activity( int activity) {
        Intent intent = new Intent(this, MainActivity.class);
       intent.putExtra("activity", activity);
        startActivity(intent);
        finish();
    }

    public void volleyGet(){

        final TextView textView = (TextView) findViewById(R.id.editTextOldPwd);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://159.65.191.124:3000/ping";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                try {
                    desc = response.getString("message");
                    textView.setText((desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("nope");

                error.printStackTrace();
            }

    });

        queue.add(getRequest);

    }

    public void volleyPostSign(){

        final TextView textViewUser = (TextView) findViewById(R.id.editTextOldPwd);
        final TextView textViewPass = (TextView) findViewById(R.id.editTextUserPassword);

        final String userName = textViewUser.getText().toString();
        String password = textViewPass.getText().toString();
        String postUrl = " http://159.65.191.124:3000/create/user";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", userName);
            postData.put("password", password);

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
                    if (desc.equals("User Created!"))
                    {
                        textViewUser.setText("");
                        textViewPass.setText("");
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
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void volleyPostLogin(){

        final TextView textViewUser = findViewById(R.id.editTextOldPwd);
        final TextView textViewPass = findViewById(R.id.editTextUserPassword);

        final String userName = textViewUser.getText().toString();
        final String password = textViewPass.getText().toString();

        if (userName.matches("") || password.matches("")) {
            Context context = getApplicationContext();
            CharSequence created = "Username or Password missing!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, created, duration);
            toast.show();
        }
        else {

            String postUrl = "http://159.65.191.124:3000/create/session";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("name", userName);
                postData.put("password", password);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String desc;
                    String token;
                    Context context = getApplicationContext();

                    //CharSequence text = "Signed up Successfully!";
                    int duration = Toast.LENGTH_SHORT;

                    try {
                        desc = response.getString("message");
                        token = response.getString("token");

                        String toastMessage = desc + " " + userName + " logged in!";
                        final Toast toast1 = Toast.makeText(context, desc, duration);
                        textViewUser.setText((desc));
                        //toast.setGravity(Gravity.BOTTOM|Gravity.LEFT,0,0);
                        toast1.show();
                        if (desc.equals("Session Created!")) {
                            textViewUser.setText("");
                            textViewPass.setText("");
                            final Toast toast = Toast.makeText(context, toastMessage, duration);
                            toast.show();
                            // i.putExtra("UserToken", token);
                            int activity = 1;

                            openLogin_Activity(activity);
                            SharedPreferences sharedPreferences = getSharedPreferences("MiddleGuyPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("username", userName);
                            myEdit.putString("password", password);
                            myEdit.putString("token", token);
                            // myEdit.putString("picture",defaultImage);
                            myEdit.commit();
                        } else {
                            toast1.show();
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
            });

            requestQueue.add(jsonObjectRequest);
        }

    }



}