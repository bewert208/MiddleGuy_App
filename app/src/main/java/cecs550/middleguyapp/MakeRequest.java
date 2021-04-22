package cecs550.middleguyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MakeRequest extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_request);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final TextView yourRequest = findViewById(R.id.editTextRequest);
        final TextView title = findViewById(R.id.editTextTitle);
        Button submit = findViewById(R.id.buttonSubmitRequest);
        Button back = findViewById(R.id.buttonBack);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String userText =  yourRequest.getText().toString();
               String userTitle = title.getText().toString();

                if (userText.matches("") || userTitle.matches("")){

                    Context context = getApplicationContext();
                    CharSequence created = "No title or request entered!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast1 = Toast.makeText(context, created, duration);
                    toast1.show();

                }else {
                    yourRequest.setText("");
                    title.setText("");
                    Context context = getApplicationContext();
                    CharSequence text = "Request created!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    openMainAct();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainAct();
            }
        });
    }

    public void openMainAct() {
        int activity = 1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("activity",activity);
        startActivity(intent);
        finish();
    }
}