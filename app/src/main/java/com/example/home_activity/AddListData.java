package com.example.home_activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_activity.adapter.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddListData extends AppCompatActivity {
    EditText fullName,fatherName,motherName,nidNumber,address;
    Button addFire;
    TextView displayView;
    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    List<AddData> listItem;

    ProgressDialog progressDialog;

    //DatabaseReference reqestRf = db.getReference("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list_data);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItem = new ArrayList<>();


//        loadData();


        fullName = findViewById(R.id.full_name_id);
        fatherName = findViewById(R.id.father_name_id);
        motherName = findViewById(R.id.mother_name_id);
        nidNumber = findViewById(R.id.nid_id);
        address = findViewById(R.id.address_id);
        addFire = findViewById(R.id.submit_button_id);
//        displayView = findViewById(R.id.display_id);



        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        addFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_fullName = fullName.getText().toString();
                String user_fatherName = fatherName.getText().toString();
                String user_motherName = motherName.getText().toString();
                String user_nidnumber = nidNumber.getText().toString().trim();
                String user_address = address.getText().toString();

                if (!TextUtils.isEmpty(user_fullName)
                        && !TextUtils.isEmpty(user_fatherName)
                        && !TextUtils.isEmpty(user_motherName)
                        && !TextUtils.isEmpty(user_nidnumber)
                        && !TextUtils.isEmpty(user_address) && (user_nidnumber.length() == 10 || user_nidnumber.length() == 17)){

                    String myCurrentDateTime = DateFormat.getDateTimeInstance()
                            .format(Calendar.getInstance().getTime());
                    AddData sendData = new AddData(user_fullName,user_fatherName,user_motherName,user_nidnumber,user_address);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(myCurrentDateTime).setValue(sendData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddListData.this, "Add Successfully", Toast.LENGTH_SHORT).show();
                                fullName.setText("");
                                fatherName.setText("");
                                motherName.setText("");
                                nidNumber.setText("");
                                address.setText("");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddListData.this, "Error to add data", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(AddListData.this, "Error found", Toast.LENGTH_SHORT).show();

                }
            }
        });



//    registerForContextMenu(recyclerView);
}

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getMenuInflater().inflate(R.menu.edit_delete_context_menu, menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        int itemId = item.getItemId();
//        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        int position = menuInfo.position;
//
//        AddData addData = listItem.get(position);
//
//        switch (itemId){
//            case R.id.editId:
//                Intent intent = new Intent(this, UpdateActivity.class);
//                intent.putExtra("AddData", addData);
//                startActivity(intent);
//
//                break;
//            case R.id.deleteId:
////                studentReference.child(student.getId()).removeValue();
//                Toast.makeText(this, "Opps ! Delete option deleted :D Hahaha ;)", Toast.LENGTH_SHORT).show();
//
//                break;
//        }
//
//        return super.onContextItemSelected(item);
//    }



    @Override
    protected void onStart() {
        super.onStart();





        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = "";
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    AddData addData = itemSnapshot.getValue(AddData.class);

//                    String oName = addData.getFull_Name();
//                    String fName  =addData.getFather_Name();
//                    String mName = addData.getMother_Name();
//                    String nNumber = addData.getNid_Number();
//                    String aAddress = addData.getAddress_loc();

//                    data = data +"Name: "+oName + "\nF.Name: "+fName+"\nM.Name: "
//                            +mName+"\nNID no: "+nNumber+"\nAddress: "+aAddress+"\n\n";

                    listItem.add(addData);


                }
                myAdapter = new MyAdapter(listItem,getApplicationContext());
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddListData.this, "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });

        /*requestRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    return;
                }
                int i=0;
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    AddData addData = documentSnapshot.toObject(AddData.class);
                    String oName = addData.getFull_Name();
                    String fName  =addData.getFather_Name();
                    String mName = addData.getMother_Name();
                    String nNumber = addData.getNid_Number();
                    String aAddress = addData.getAddress_loc();
                    i++;


                    data = "Name: "+oName + "\nF.Name: "+fName+"\nM.Name: "
                            +mName+"\nNID no: "+nNumber+"\nAddress: "+aAddress+"\n\n";
                }
                displayView.setText(data);
                Log.d(TAG, "onEvent: "+i);
            }
        });*/
    }
}
