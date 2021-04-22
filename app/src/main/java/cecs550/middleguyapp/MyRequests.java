package cecs550.middleguyapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyRequests extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_requests);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = getIntent().getExtras();
        int activity = bundle.getInt("activity");


            final String title = bundle.getString("title");
            String desc = bundle.getString("desc");
            int pos =  bundle.getInt("pos");




        listView = findViewById(R.id.lvReq);

        final ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> arrayListTitle = new ArrayList<>();

        arrayList.add("Need a used car for getting to work.");
        arrayList.add("Does anyone know any good CECS tutor that are not too expensive?");
        arrayList.add("I am looking for a PS5. These scalper prices are killing me!!");
        arrayList.add("Anyone know of any good summer jobs for a college student?");
        arrayList.add("I am looking for a used Nintendo Switch, let me know if you have one for sale.");


        arrayListTitle.add(("Looking for a car"));
        arrayListTitle.add(("Looking for Tutor."));
        arrayListTitle.add(("Looking to buy PS5."));
        arrayListTitle.add(("Looking for summer Job."));
        arrayListTitle.add(("Nintendo Switch??"));

        arrayList.set(pos,desc);
        arrayListTitle.set(pos,title);

        if(activity == 4)
        {
            arrayList.remove(pos);
            arrayListTitle.remove(pos);
        }

        ArrayAdapter arrayAdapterTitle = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayListTitle);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setAdapter(arrayAdapterTitle);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MyRequests.this,"clicked item:"+i+" "+arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
                String detail = arrayList.get(i);
                String title = arrayListTitle.get(i);
                openEditRequest(detail, title, i);


            }
        });
    }

    public void openEditRequest(String detail, String title, int i) {
        Intent intent = new Intent(this, EditMyRequest.class);
        intent.putExtra("detail",detail);
        intent.putExtra("title",title);
        intent.putExtra("pos",i);
        startActivity(intent);
        finish();
    }
}