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

public class PassWordChange extends AppCompatActivity {

    public Button changePswBtn;
    public Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_word_change);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        changePswBtn = findViewById(R.id.changePasswordBtn);
        back = findViewById(R.id.backBtn);
        changePswBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                editPassword();


            }
        });


        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openEditAct();

            }
        });
    }



    public void editPassword() {
        final SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        final String token = sh.getString("token", "empty");


        TextView name = findViewById(R.id.editTextUserNamePsw);
        TextView psw = findViewById(R.id.editTextOldPwd);
        TextView newPsw = findViewById(R.id.editTextNewPwd);
        String userName = name.getText().toString();
        String password = psw.getText().toString();
        final String newPassword = newPsw.getText().toString();

        if (userName.matches("") || password.matches("") || newPassword.matches("")) {
            Context context = getApplicationContext();
            CharSequence created = "A field is missing!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, created, duration);
            toast.show();
        }
        else {


        String postUrl = "http://159.65.191.124:3000/authenticated/doubly/user/password_change";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("name", userName);
            postData.put("password", password);
            postData.put("new_password", newPassword);

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


                    toast.show();
                    if (desc.equals("User password changed!"))
                    {
                        openEditAct();
                    }


                    SharedPreferences.Editor myEdit = sh.edit();
                    myEdit.putString("password", newPassword);
                    myEdit.commit();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", token);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
    }

    public void openEditAct() {
        Intent intent = new Intent(this, EditProfile.class);
        startActivity(intent);
        finish();
    }


}