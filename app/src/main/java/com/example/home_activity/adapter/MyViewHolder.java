package com.example.home_activity.adapter;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_activity.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView fulname,fatherName,motherName,NID,address;
    ImageView optionDot;
    CardView cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.cardViewId);

        fulname = itemView.findViewById(R.id.fulnameId);
        fatherName = itemView.findViewById(R.id.fathernameId);
        motherName = itemView.findViewById(R.id.mothernameId);
        NID = itemView.findViewById(R.id.nidId);
        address = itemView.findViewById(R.id.addressId);
        optionDot = itemView.findViewById(R.id.optionDotId);


    }
}
