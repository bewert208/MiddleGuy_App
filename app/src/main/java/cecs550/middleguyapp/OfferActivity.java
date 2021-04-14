package cecs550.middleguyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        Bundle bundle = getIntent().getExtras();

        String desc = bundle.getString("desc");
        TextView textDesc = findViewById(R.id.textViewDesc);
        textDesc.setText(desc);
    }
}