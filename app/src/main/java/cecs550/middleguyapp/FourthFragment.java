package cecs550.middleguyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FourthFragment extends Fragment {

    public FourthFragment(){
    }

    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //frag4 = new FourthFragment();

        View view = inflater.inflate(R.layout.fragment_3, container,
                false);
        /*listView =(ListView)getActivity().findViewById(R.id.reqList);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("testing dad");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);*/
        return view;
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_3,
        // container, false);


    }


}




