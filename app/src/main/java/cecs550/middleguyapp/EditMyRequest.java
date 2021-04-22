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
    public Button delete;
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
        final String poster = bundle.getString("detail");
        final int pos =  bundle.getInt("pos");

        title = findViewById(R.id.editTextTitle);
        detail = findViewById(R.id.editTextDetail);
        title.setText(desc);
        detail.setText(poster);

        back = findViewById(R.id.button2Back);
        update = findViewById(R.id.buttonUpdate);
        delete = findViewById(R.id.deleteBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("");
                detail.setText("");

                openRequests(desc,poster,pos,1);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           String titleTxt = title.getText().toString();
           String descTxt = detail.getText().toString();

                openRequests(titleTxt,descTxt,pos,1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empty = "";
                openRequests(empty,empty,pos,4);
            }
        });
    }

    public void openRequests(String title, String desc, int pos, int activity) {
        Intent intent = new Intent(this, MyRequests.class);
        intent.putExtra("pos",pos);
        intent.putExtra("title",title);
        intent.putExtra("desc",desc);
        intent.putExtra("activity", activity);
        startActivity(intent);
        finish();
    }
}