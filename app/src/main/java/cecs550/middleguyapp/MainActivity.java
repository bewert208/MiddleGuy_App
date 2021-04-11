package cecs550.middleguyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public Fragment frag1;
    public Fragment frag2;
    public Fragment frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        //CharSequence text = "Signed up Successfully!";
        int duration = Toast.LENGTH_SHORT;


        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username") + " logged in!";
        String token = extras.getString("token");
        final Toast toast = Toast.makeText(context, username, duration);
        toast.show();

        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putString("token",token);


       // FragmentTransaction fragmentTramsaction = getSupportFragmentManager().beginTransaction();

        //fragmentTramsaction.add(R.id.flFragment,new )

        frag1 = new FirstFragment();
        frag2 = new SecondFragment();
        frag3 = new ThirdFragment();

        setFragment(frag1);
       BottomNavigationView navigation = (BottomNavigationView)
               findViewById(R.id.bottomNavigationView); navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flFragment,fragment).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle extras = getIntent().getExtras();
            String username = extras.getString("username");
            String token = extras.getString("token");
            Bundle bundle = new Bundle();
            bundle.putString("username",username);
            bundle.putString("token",token);
            switch (item.getItemId()) {
                case R.id.nav_home:
                    setFragment(frag1);
                    //openLogin_Activity();
                    break;

                case  R.id.nav_chat:
                    setFragment(frag2);
                    break;

                case R.id.nav_person:
                    frag3.setArguments(bundle);
                    setFragment(frag3);
                    break;
            }
            return true;
        }
    };


}

