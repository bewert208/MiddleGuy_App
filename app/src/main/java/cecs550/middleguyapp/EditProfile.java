package cecs550.middleguyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private static final String TAG = "EditProfile";

    private static int RESULT_LOAD_IMAGE = 1;
    public Button loginBtn;
    public Button changeImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        loginBtn = findViewById(R.id.updateBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmail();
                editProfile();
                editBio();
                editPhone();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        openMainAct();
                    }
                }, 1000);

            }


        });
        SharedPreferences sh = this.getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        final String picture = sh.getString("picture","empty");
        ImageView imageView = findViewById(R.id.editProfileImg);
        imageView.setImageBitmap(BitmapFactory.decodeFile(picture));

        changeImgBtn = findViewById(R.id.changeImageBtn);

        changeImgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = findViewById(R.id.editProfileImg);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sh.edit();
            myEdit.putString("picture",picturePath);
            myEdit.commit();


        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }


    public void editProfile() {
        final SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        String password = sh.getString("password","empty");

        TextView TextViewNewName = (TextView) findViewById(R.id.editTextNewName);
        final String newName = TextViewNewName.getText().toString();

        if(!newName.isEmpty()) {

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
                        if (desc.equals("User name updated!")) {

                            SharedPreferences.Editor myEdit = sh.edit();
                            myEdit.putString("username", newName);
                            myEdit.commit();


                            //openMainAct();
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

    public void editBio() {
        SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        String password = sh.getString("password","empty");

        TextView bio = (TextView) findViewById(R.id.editTextBio);
        String userBio = bio.getText().toString();

        if (!userBio.isEmpty()) {

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
                        if (desc.equals("Bio updated!")) {


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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", token);
                    return params;
                }
            };

            requestQueue.add(jsonObjectRequest);
            // openMainAct();
        }
    }

    public void editEmail() {

        SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username","empty");
        final String token = sh.getString("token","empty");
        String password = sh.getString("password","empty");

        TextView email = (TextView) findViewById(R.id.editTextEmail);
        String userEmail = email.getText().toString();

        if(!userEmail.isEmpty()) {


            String postUrl = "http://159.65.191.124:3000/authenticated/doubly/user/email_change";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("name", userName);
                postData.put("password", password);
                postData.put("new_email", userEmail);

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
                        if (desc.equals("Email updated!")) {

                            //openMainAct();
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

    public void editPhone() {
        SharedPreferences sh = getSharedPreferences("MiddleGuyPref", Context.MODE_PRIVATE);
        String userName = sh.getString("username", "empty");
        final String token = sh.getString("token", "empty");
        String password = sh.getString("password", "empty");

        TextView phone = (TextView) findViewById(R.id.editTextPhone);
        String userPhone = phone.getText().toString();

        if (!userPhone.isEmpty()) {


            String postUrl = "http://159.65.191.124:3000/authenticated/doubly/user/phone_change";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject postData = new JSONObject();
            try {
                postData.put("name", userName);
                postData.put("password", password);
                postData.put("new_phone", userPhone);

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
                        if (desc.equals("Phone updated!")) {

                            //openMainAct();
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



    public void openMainAct() {
       int activity = 0;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("activity",activity);
        startActivity(intent);
        finish();
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}