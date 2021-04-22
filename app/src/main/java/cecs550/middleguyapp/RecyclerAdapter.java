package cecs550.middleguyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return  DummyData.title.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView userName;
        private TextView desc;
        private ImageView profileImg;

        private final Context context;

        public ListViewHolder(View itemView){

            super(itemView);
            context = itemView.getContext();
            this.userName = itemView.findViewById(R.id.userName);
            this.desc = itemView.findViewById(R.id.desc);
            this.profileImg = itemView.findViewById(R.id.userImg);
            itemView.setOnClickListener(this);
            itemView.setTag(this);
        }

        public void bindView(int position)
        {
            userName.setText(DummyData.title[position]);
            desc.setText((DummyData.description[position]));
            profileImg.setImageResource(DummyData.picturePath[position]);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;
            int position = getAdapterPosition();
            String desc = DummyData.description[position];
            String poster = DummyData.title[position];
            int image = DummyData.picturePath[position];


            Bundle bundle = new Bundle();
            bundle.putString("desc",desc);
            bundle.putString("poster",poster);
            bundle.putInt("image",image);

            intent = new Intent(context,OfferActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);



        }


    }
}
