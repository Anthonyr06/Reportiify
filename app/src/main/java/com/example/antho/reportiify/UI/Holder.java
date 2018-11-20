package com.example.antho.reportiify.UI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.antho.reportiify.R;

public class Holder extends RecyclerView.ViewHolder{

    ImageView img;
    TextView textViewHead,textViewDesc, textViewLocation;

    Holder(View itemView) {
        super(itemView);

        textViewHead = itemView.findViewById(R.id.textViewHead);
        textViewDesc = itemView.findViewById(R.id.textViewDes);
        textViewLocation = itemView.findViewById(R.id.textViewLocation);
        img = itemView.findViewById(R.id.cardImg);
    }
}
