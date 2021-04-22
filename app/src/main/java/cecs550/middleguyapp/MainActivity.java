package cecs550.middleguyapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    public Fragment frag1;
    public Fragment frag2;
    public Fragment frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        int activity = extras.getInt("activity");

        frag1 = new HomeFragment();
        frag2 = new ChatFragment();
        frag3 = new ProfileFragment();






        setFragment(frag1);
        BottomNavigationView navigation = (BottomNavigationView)
                findViewById(R.id.bottomNavigationView); navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                if (activity == 0)
                {
                    BottomNavigationView bottomNavigationView;
                    bottomNavigationView =  findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    bottomNavigationView.setSelectedItemId(R.id.nav_person);
                }
                else if (activity == 1) {
                    BottomNavigationView bottomNavigationView;
                    bottomNavigationView =  findViewById(R.id.bottomNavigationView);
                    bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                }

    }


    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flFragment,fragment).commit();

    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    setFragment(frag1);
                    break;

                case  R.id.nav_chat:
                    setFragment(frag2);
                    break;

                case R.id.nav_person:
                    setFragment(frag3);
                    break;
            }
            return true;
        }
    };


}

