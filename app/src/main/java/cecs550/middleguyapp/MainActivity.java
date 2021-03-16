package cecs550.middleguyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public Fragment frag1;
    public Fragment frag2;
    public Fragment frag3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = new FirstFragment();
        frag2 = new SecondFragment();
        frag3 = new ThirdFragment();

        setFragment(frag1);
       BottomNavigationView navigation = (BottomNavigationView)
               findViewById(R.id.bottomNavigationView); navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

//    private void replaceFragment(){
//       Fragment fragment = new Fragment();
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.flFragment, fragment);
//        fragmentTransaction.commit();
//    }

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