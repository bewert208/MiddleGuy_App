package cecs550.middleguyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditMyRequest extends AppCompatActivity {

    public Button back;
    public Button update;
    public TextView title;
    public TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_request);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle bundle = getIntent().getExtras();

        final String desc = bundle.getString("title");
        String poster = bundle.getString("detail");

        title = findViewById(R.id.editTextTitle);
        detail = findViewById(R.id.editTextDetail);
        title.setText(desc);
        detail.setText(poster);

        back = findViewById(R.id.button2Back);
        update = findViewById(R.id.buttonUpdate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("");
                detail.setText("");
                openRequests();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openRequests();
            }
        });
    }

    public void openRequests() {
        Intent intent = new Intent(this, MyRequests.class);
        startActivity(intent);
        finish();
    }
}