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

public class MyPost extends AppCompatActivity {

    RecyclerView mrecyclerView;
    ArrayList<PostHousedetails> mhlist;
    MypostViewadapter madapter;
    String myuname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        Intent i = this.getIntent();
        myuname = i.getExtras().getString("username");


        mrecyclerView = (RecyclerView) findViewById(R.id.myhouses_list);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference mydata = FirebaseDatabase.getInstance().getReference();
        Query query = mydata.child("Home Details").orderByChild("uhname").equalTo(myuname);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mhlist = new ArrayList<PostHousedetails>();
                for(DataSnapshot d1 : dataSnapshot.getChildren()){
                    PostHousedetails p = d1.getValue(PostHousedetails.class);
                    mhlist.add(p);
                }
                madapter = new MypostViewadapter(MyPost.this,mhlist);
                mrecyclerView.setAdapter(madapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
