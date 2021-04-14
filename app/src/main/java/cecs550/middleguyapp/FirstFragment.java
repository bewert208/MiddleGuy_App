package cecs550.middleguyapp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FirstFragment extends Fragment {

    public FirstFragment(){
    }

    /*private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)
                    view.getTag();
            int position = viewHolder.getAdapterPosition();
            String shoeID = DummyData.description[position];
            Intent intent = new Intent(getActivity(), OfferActivity.class);
            intent.putExtra("shoeID",shoeID);
            startActivity(intent);
        }
    };*/



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


        RecyclerView recyclerView = rootView.findViewById(R.id.recylcerViewPost);
        RecyclerAdapter listAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);*/
        return rootView;

    }



}




