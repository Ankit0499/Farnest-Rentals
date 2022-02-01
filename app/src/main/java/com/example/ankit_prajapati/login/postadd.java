package com.example.ankit_prajapati.login;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class postadd extends AppCompatActivity {

    private TextView t1;
    private RadioButton rb1,rb2;
    private Button s1;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postadd);

        t1 = (TextView)findViewById(R.id.tv1);
        rb1 = (RadioButton)findViewById(R.id.home_post);
        rb2 = (RadioButton)findViewById(R.id.shop_post);
        s1 = (Button)findViewById(R.id.osub);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                t1.setText(userProfile.getName()+", You are here to rent for...");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb1.isChecked()){
                    Toast.makeText(postadd.this,"You selected : "+rb1.getText().toString(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(postadd.this,postaddetail.class));
                }
                else if(rb2.isChecked()){
                    Toast.makeText(postadd.this,"You selected : "+rb2.getText().toString(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(postadd.this,postshopd.class));
                }

            }
        });

    }
}
