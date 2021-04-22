package cecs550.middleguyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class ProfileFragment extends Fragment {

    public ProfileFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        SharedPreferences sh = this.getActivity().getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        final String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        final String password = sh.getString("password","empty");
        final String picture = sh.getString("picture","empty");



        View view = inflater.inflate(R.layout.fragment_3, container,
                false);
        Button btn = (Button) view.findViewById(R.id.btnMyRequests);
        ImageView profileImg = view.findViewById(R.id.imageView2);
        profileImg.setImageBitmap(BitmapFactory.decodeFile(picture));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyRequests();
            }
        });

        Button logoutBtn = (Button) view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String postUrl = "http://159.65.191.124:3000/authenticated/logout";
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

                JSONObject postData = new JSONObject();
                try {
                    postData.put("name", userName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String desc;
                        Context context = getActivity().getApplicationContext();
                        ;
                        int duration = Toast.LENGTH_SHORT;

                        try {
                            desc = response.getString("message");
                            CharSequence text = "Logout Successful!";
                            final Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            if (desc.equals("User Session Token Deleted!"))
                            {
                                openLogin_Activity();
                                SharedPreferences sh = getActivity().getSharedPreferences("MiddleGuyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sh.edit();
                                editor.clear();
                                editor.apply();
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
        });

        final Button editName = (Button) view.findViewById(R.id.editPro);
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfile();


            }
        });

        final TextView TextViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        final TextView TextViewBio = (TextView) view.findViewById(R.id.textViewBio);
        final TextView TextViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
        final TextView TextViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        //final String newName = TextViewUserName.getText().toString();

        String postUrl = "http://159.65.191.124:3000/authenticated/user/name_get";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JSONObject postData = new JSONObject();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = (getActivity().getApplicationContext());

                //CharSequence text = "Signed up Successfully!";
                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("name");
                    TextViewUserName.setText((desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                    TextViewUserName.setText("nope");
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


        String postUrlBio = "http://159.65.191.124:3000/authenticated/user/bio_get";

        JSONObject postDataBio = new JSONObject();

        JsonObjectRequest jsonObjectRequestBio = new JsonObjectRequest(Request.Method.POST, postUrlBio, postDataBio, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = (getActivity().getApplicationContext());

                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("bio");
                    TextViewBio.setText((desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                    CharSequence text = "Obtaining bio error";
                    final Toast toast = Toast.makeText(context, text, duration);
                    //toast.show();
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


        String postUrlEmail = "http://159.65.191.124:3000/authenticated/user/email_get";

        JSONObject postDataEmail = new JSONObject();

        JsonObjectRequest jsonObjectRequestEmail = new JsonObjectRequest(Request.Method.POST, postUrlEmail, postDataEmail, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = (getActivity().getApplicationContext());

                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("email");
                    TextViewEmail.setText((desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                    CharSequence text = "Obtaining email error";
                    final Toast toast = Toast.makeText(context, text, duration);
                    //toast.show();
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

        String postUrlPhone = "http://159.65.191.124:3000/authenticated/user/phone_get";

        JSONObject postDataPhone = new JSONObject();

        JsonObjectRequest jsonObjectRequestPhone = new JsonObjectRequest(Request.Method.POST, postUrlPhone, postDataPhone, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = (getActivity().getApplicationContext());

                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("phone");

                    TextViewPhone.setText((desc));

                } catch (JSONException e) {
                    e.printStackTrace();
                    CharSequence text = "Obtaining phone error";
                    final Toast toast = Toast.makeText(context, text, duration);
                    //toast.show();
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
        requestQueue.add(jsonObjectRequestBio);
        requestQueue.add(jsonObjectRequestEmail);
        requestQueue.add(jsonObjectRequestPhone);

        return view;

    }


    public void openMyRequests() {
        Intent intent = new Intent(getActivity(), MyRequests.class);
        int i = 0;
        intent.putExtra("activity",i);
        intent.putExtra("title","Looking for a car.");
        intent.putExtra("desc","Need a used car for getting to work.");
        intent.putExtra("pos",0);
        startActivity(intent);
    }

    public void openLogin_Activity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    public void openEditProfile() {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }




}




