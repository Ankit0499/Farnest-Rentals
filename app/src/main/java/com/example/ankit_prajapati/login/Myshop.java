package com.example.ankit_prajapati.login;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Myshop extends AppCompatActivity {

    RecyclerView msrecyclerView;
    ArrayList<PostShopdetails> mslist;
    MyShopViewadapter sadapter;
    String myuname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent i = this.getIntent();
        myuname = i.getExtras().getString("Myusername");

        msrecyclerView = (RecyclerView) findViewById(R.id.myshop_list);
        msrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mydata = FirebaseDatabase.getInstance().getReference();
        Query query = mydata.child("Shop Details").orderByChild("usname").equalTo(myuname);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mslist = new ArrayList<PostShopdetails>();
                for(DataSnapshot d1 : dataSnapshot.getChildren()){
                    PostShopdetails p = d1.getValue(PostShopdetails.class);
                    mslist.add(p);
                }
                sadapter = new MyShopViewadapter(Myshop.this,mslist);
                msrecyclerView.setAdapter(sadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}