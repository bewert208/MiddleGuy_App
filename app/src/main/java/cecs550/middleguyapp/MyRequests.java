package cecs550.middleguyapp;

import androidx.appcompat.app.AppCompatActivity;

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

         listView =(ListView)findViewById(R.id.lvReq);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("I am looking for a used Nintendo Switch.");
        arrayList.add("Does anyone know any good CECS tutor that are not too expensive?");
        arrayList.add("I am looking for a PS5. These scalper prices are killing me!!");
        arrayList.add("Anyone know of any good summer jobs for a college student?");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MyRequests.this,"clicked item:"+i+" "+arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}