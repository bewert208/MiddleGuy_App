package cecs550.middleguyapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FirstFragment extends Fragment {

    public FirstFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_1, container,
                false);



        RecyclerView recyclerView = rootView.findViewById(R.id.recylcerViewPost);
        RecyclerAdapter listAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton createReq = rootView.findViewById(R.id.floatingActionBtnRequest);
        createReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateRequest();
            }
        });




        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.drop_down_menu, menu);

    }

    public void openCreateRequest() {
        Intent intent = new Intent(getActivity(), MakeRequest.class);
        startActivity(intent);
    }







}




