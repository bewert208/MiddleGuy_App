package cecs550.middleguyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class ThirdFragment extends Fragment {

    public ThirdFragment(){
    }
    //public Fragment frag4;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final String userName = getArguments().getString("username");
        final String token = getArguments().getString("token");

        View view = inflater.inflate(R.layout.fragment_3, container,
                false);
        Button btn = (Button) view.findViewById(R.id.btnMyRequests);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
                //goToFrag();
            }
        });

        final Button logoutBtn = (Button) view.findViewById(R.id.logoutBtn);
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
                        String token;
                        Context context = getActivity().getApplicationContext();

                        //CharSequence text = "Signed up Successfully!";
                        int duration = Toast.LENGTH_SHORT;

                        try {
                            desc = response.getString("message");
                            CharSequence text = "Logout Successful!";
                            final Toast toast = Toast.makeText(context, text, duration);
                            //textViewUser.setText((desc));
                            toast.show();
                            if (desc.equals("User Session Token Deleted!"))
                            {
                                //textViewUser.setText("");
                                //textViewPass.setText("");

                                // i.putExtra("UserToken", token);
                                openLogin_Activity();
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

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_3,
               // container, false);


    }

    public void goToFrag()
    {
        Fragment fragment = new FourthFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openActivity2() {
        Intent intent = new Intent(getActivity(), MyRequests.class);
        startActivity(intent);
    }

    public void openLogin_Activity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

    }

   /* public void volleyPostLogout(){

        //final TextView textViewUser = (TextView) findViewById(R.id.editTextUserName);
        //final TextView textViewPass = (TextView) findViewById(R.id.editTextUserPassword);


        //String userName = textViewUser.getText().toString();
        //String password = textViewPass.getText().toString();
        String postUrl = "http://159.65.191.124:3000/authenticated/logout";
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
                    final Toast toast = Toast.makeText(context, desc, duration);
                    //textViewUser.setText((desc));
                    toast.show();
                    if (desc.equals("Session Created!"))
                    {
                        textViewUser.setText("");
                        textViewPass.setText("");

                        // i.putExtra("UserToken", token);
                        openLogin_Activity(token);
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

    }*/


}




