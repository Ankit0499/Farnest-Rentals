package com.example.ankit_prajapati.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView mydp;
    private TextView myname;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    RecyclerView recyclerView;
    ArrayList<PostHousedetails> hlist;
    HouseAdapter adapter;
    String name1;
    BottomNavigationView bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user = FirebaseAuth.getInstance().getCurrentUser();

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
        }else{
            //no connection
            Toast.makeText(this, "Connect with Internet", Toast.LENGTH_SHORT).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.houses_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Home Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hlist = new ArrayList<PostHousedetails>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    PostHousedetails p = dataSnapshot1.getValue(PostHousedetails.class);
                    hlist.add(p);
                }
                adapter = new HouseAdapter(Main2Activity.this,hlist);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(Main2Activity.this, "Not Found", Toast.LENGTH_SHORT).show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        drawerLayout = (DrawerLayout) findViewById(R.id.hl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navview = navigationView.inflateHeaderView(R.layout.header);

        mydp=(ImageView)navview.findViewById(R.id.dpmy);
        myname=(TextView)navview.findViewById(R.id.myprofilename);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                myname.setText(userProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Main2Activity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference().child("Users");
        storageReference.child(user.getUid()).child("MyProfile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(mydp);
            }
        });

        bnv =(BottomNavigationView)findViewById(R.id.navigationbottom);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.myprofile:{
                        startActivity(new Intent(Main2Activity.this,ProfileActivity.class));
                        break;
                    }
                    case R.id.shops:{
                        startActivity(new Intent(Main2Activity.this,shopview.class));
                        break;
                    }
                    case R.id.homes:{
                        startActivity(new Intent(Main2Activity.this,Main2Activity.class));
                        break;
                    }
                }
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        name1 = myname.getText().toString();
        int item = menuItem.getItemId();
        switch (item) {
            case R.id.logoutMenu:{
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
                break;
            }
            case R.id.profileMenu:{
                startActivity(new Intent(Main2Activity.this,ProfileActivity.class));
                break;
            }
            case R.id.addpost:{
                startActivity(new Intent(Main2Activity.this,postadd.class));
                break;
            }
            case R.id.feedback:{
                startActivity(new Intent(Main2Activity.this,Feedback.class));
                break;
            }
            case R.id.viewshops:{
                startActivity(new Intent(Main2Activity.this,shopview.class));
                break;
            }
            case R.id.myhouse:{
                Intent i = new Intent(Main2Activity.this,MyPost.class);
                i.putExtra("username",name1);
                startActivity(i);
                break;
            }
            case R.id.myshop:{
                Intent i = new Intent(Main2Activity.this,Myshop.class);
                i.putExtra("Myusername",name1);
                startActivity(i);
                break;
            }
        }
        return false;
    }

}
