package com.example.home_activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_activity.AddData;
import com.example.home_activity.R;
import com.example.home_activity.UpdateActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<AddData> MyItem;
    Context context;
    RecyclerView recyclerView;

    private DatabaseReference databaseReference;


    public MyAdapter(List<AddData> myItem, Context context) {
        MyItem = myItem;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sample,viewGroup,false);
        MyViewHolder MVH = new MyViewHolder(view);
        return MVH;


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final AddData itemPosition = MyItem.get(i);

        myViewHolder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

        myViewHolder.fulname.setText(itemPosition.getFull_Name());
        myViewHolder.fatherName.setText(itemPosition.getFather_Name());
        myViewHolder.motherName.setText(itemPosition.getMother_Name());
        myViewHolder.NID.setText(itemPosition.getNid_Number());
        myViewHolder.address.setText(itemPosition.getAddress_loc());

//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Good Job !", Toast.LENGTH_SHORT).show();
//            }
//        });

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });




        myViewHolder.optionDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context,myViewHolder.optionDot);
                //inflating menu from xml resource
                popup.inflate(R.menu.edit_delete_context_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.editId:
                                //handle menu1 click
                                Toast.makeText(context, "Item Edit Clicked !", Toast.LENGTH_SHORT).show();
                                return true;

                            case R.id.deleteId:
                                //handle menu2 click
                                Toast.makeText(context, "Item Delete Clicked !", Toast.LENGTH_SHORT).show();

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });

    }


    @Override
    public int getItemCount() {
        return MyItem.size();
    }
}
