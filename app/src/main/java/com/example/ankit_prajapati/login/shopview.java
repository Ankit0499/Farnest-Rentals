package com.example.ankit_prajapati.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shopview extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<PostShopdetails> hlist;
    shopadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopview);

        recyclerView = (RecyclerView) findViewById(R.id.shop_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Shop Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hlist = new ArrayList<PostShopdetails>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    PostShopdetails p = dataSnapshot1.getValue(PostShopdetails.class);
                    hlist.add(p);
                }
                adapter = new shopadapter(shopview.this,hlist);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(shopview.this, "Not Found", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
