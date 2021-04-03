package cecs550.middleguyapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import static android.app.Activity.RESULT_OK;

public class ThirdFragment extends Fragment {

    public ThirdFragment(){
    }
    //public Fragment frag4;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //frag4 = new FourthFragment();

        View view = inflater.inflate(R.layout.fragment_3, container,
                false);
        Button btn = (Button) view.findViewById(R.id.btnMyRequests);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
                //goToFrag();
            }
        });

        return view;
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_3,
               // container, false);


    }

    public void goToFrag()
    {
        Fragment fragment = new FourthFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void openActivity2() {
        Intent intent = new Intent(getActivity(), MyRequests.class);
        startActivity(intent);
    }


}




