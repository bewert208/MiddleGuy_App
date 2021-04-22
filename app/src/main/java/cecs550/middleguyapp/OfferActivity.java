package cecs550.middleguyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class OfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = getIntent().getExtras();

        final String desc = bundle.getString("desc");
        String poster = bundle.getString("poster");
        int image = bundle.getInt("image");

        TextView textDesc = findViewById(R.id.textViewPostdesc);
        TextView textPoster = findViewById(R.id.textViewPoster);
        final TextView message = findViewById(R.id.editTextMessage);
        final TextView price = findViewById(R.id.editTextPrice);

        ImageView proImage = findViewById(R.id.posterImage);

        proImage.setImageResource(image);
        textPoster.setText(poster);
        textDesc.setText(desc);

        Button back = findViewById(R.id.buttonBack);
        Button sendOffer = findViewById(R.id.buttonOffer);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainAct();
            }
        });

        sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().matches("") || price.getText().toString().matches("" )) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    CharSequence text2 = "Missing field!";
                    Toast toast1 = Toast.makeText(context, text2, duration);
                    toast1.show();
                }
                else {
                    message.setText("");
                    price.setText("");
                    Context context = getApplicationContext();
                    CharSequence text = "Offer sent!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    openMainAct();
                }




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