package cecs550.middleguyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_APPEND;

public class ThirdFragment extends Fragment {

    public ThirdFragment(){
    }
    //public Fragment frag4;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //final String userName = getArguments().getString("username");
        //final String token = getArguments().getString("token");
        //final String password = getArguments().getString("password");
        SharedPreferences sh = this.getActivity().getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        final String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        final String password = sh.getString("password","empty");
        final String picture = sh.getString("picture","empty");


        /*final Intent i = new Intent(getActivity().getBaseContext(), EditProfile.class);
        i.putExtra("username",userName);
        i.putExtra("token", token);
        i.putExtra("password",password);*/

        View view = inflater.inflate(R.layout.fragment_3, container,
                false);
        Button btn = (Button) view.findViewById(R.id.btnMyRequests);
        ImageView profileImg = view.findViewById(R.id.imageView2);
        profileImg.setImageBitmap(BitmapFactory.decodeFile(picture));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
                //goToFrag();
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
                    //postData.put("authorization", token);

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
                openEditProfile(userName, password, token);


            }
        });

        /*Bundle extras = getIntent().getExtras();
        String userName = extras.getString("username");
        String password = extras.getString("password");
        final String token = extras.getString("token");*/

        final TextView TextViewUserName = (TextView) view.findViewById(R.id.textViewUserName);
        //final String newName = TextViewUserName.getText().toString();

        String postUrl = "http://159.65.191.124:3000/authenticated/user/name_get";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        JSONObject postData = new JSONObject();
        /*try {
            postData.put(" ",'');



        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String desc;
                Context context = (getActivity().getApplicationContext());

                //CharSequence text = "Signed up Successfully!";
                int duration = Toast.LENGTH_SHORT;

                try {
                    desc = response.getString("name");
                    final Toast toast = Toast.makeText(context, desc, duration);
                    TextViewUserName.setText((desc));
                    toast.show();
                    //TextViewUserName.setText(desc);
                   /* if (desc.equals("User name updated!"))
                    {

                    }*/

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

        requestQueue.add(jsonObjectRequest);




        final TextView TextViewBio = (TextView) view.findViewById(R.id.textViewBio);
        String postUrlBio = "http://159.65.191.124:3000/authenticated/user/bio_get";
        RequestQueue requestQueueBio = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                    toast.show();
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

        requestQueueBio.add(jsonObjectRequestBio);



        final TextView TextViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
        String postUrlEmail = "http://159.65.191.124:3000/authenticated/user/email_get";
        RequestQueue requestQueueEmail = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                    toast.show();
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

        requestQueueEmail.add(jsonObjectRequestEmail);


        final TextView TextViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
        String postUrlPhone = "http://159.65.191.124:3000/authenticated/user/phone_get";
        RequestQueue requestQueuePhone = Volley.newRequestQueue(getActivity().getApplicationContext());

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
                    toast.show();
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

        requestQueuePhone.add(jsonObjectRequestPhone);

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_3,
               // container, false);


    }



    /*public void goToFrag()
    {
        Fragment fragment = new FourthFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    public void openActivity2() {
        Intent intent = new Intent(getActivity(), MyRequests.class);
        startActivity(intent);
    }

    public void openLogin_Activity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

    public void openEditProfile(String username, String password, String token) {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        //intent.putExtra("username",username);
        //intent.putExtra("password",password);
        //intent.putExtra("token",token);
        startActivity(intent);
    }




}




