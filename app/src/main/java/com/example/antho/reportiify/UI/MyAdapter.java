package com.example.antho.reportiify.UI;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antho.reportiify.R;
import com.example.antho.reportiify.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends FirebaseRecyclerAdapter<ListItem, Holder> {

    Context c;

    public MyAdapter(@NonNull FirebaseRecyclerOptions<ListItem> options, Context context) {
        super(options);
        c = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull ListItem model) {
        holder.textViewDesc.setText(model.getDesc());
        holder.textViewHead.setText(model.getHead());
        holder.textViewLocation.setText(model.getLocation());
        Picasso.get().load(model.getImg()).into(holder.img);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(c)
                .inflate(R.layout.list_items,viewGroup,false);
        return new Holder(v);
    }

}