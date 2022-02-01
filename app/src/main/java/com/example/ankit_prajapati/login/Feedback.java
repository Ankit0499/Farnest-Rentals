package com.example.ankit_prajapati.login;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {

    EditText comment;
    String femail,fucomment,ratingstar;
    Button btnfeedback;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    RatingBar fr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        comment = (EditText) findViewById(R.id.edtcomment);
        btnfeedback = (Button) findViewById(R.id.btnfeedback);

        fr = (RatingBar)findViewById(R.id.rating);




        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                femail = userProfile.email;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


                btnfeedback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ratingstar = String.valueOf(fr.getRating());
                        fucomment = comment.getText().toString();


                        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Feed back").child(firebaseUser.getUid());
                        feedbackstore fud = new feedbackstore(femail,fucomment,ratingstar);
                        databaseReference.setValue(fud);
                        Toast.makeText(Feedback.this, "Successfully Feedback Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Feedback.this,Main2Activity.class));
                        finish();

            }
        });
    }
}
