package cecs550.middleguyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class FirstFragment extends ListFragment {

    public FirstFragment(){
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_1,
                container, false);*/

        View rootView = inflater.inflate(R.layout.fragment_1, container,
                false);
        /*ListView lv = (ListView)rootView.findViewById(R.id.list);
        lv.setAdapter(adaoter);*/

        String[] values = new String[] { "Message1", "Message2", "Message3" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        return rootView;

    }


}




