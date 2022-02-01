package com.example.ankit_prajapati.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class mcchomedetails extends AppCompatActivity {

    TextView h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16;
    ImageView iv1;
    String midh;
    FirebaseDatabase mref;
    Button btn5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcchomedetails);

        iv1 = (ImageView)findViewById(R.id.mhiv);
        h1 = (TextView)findViewById(R.id.mhroomv);
        h2 = (TextView)findViewById(R.id.mhbathv);
        h3 = (TextView)findViewById(R.id.mhbalv);
        h4 = (TextView)findViewById(R.id.mhfloorv);
        h5 = (TextView)findViewById(R.id.mhfurn5);
        h6 = (TextView)findViewById(R.id.mhcarv);
        h7 = (TextView)findViewById(R.id.mhsupv);
        h8 = (TextView)findViewById(R.id.mhrentv);
        h9 = (TextView)findViewById(R.id.mhotherv);
        h10 = (TextView)findViewById(R.id.mhsecv);
        h11 = (TextView)findViewById(R.id.mhmainv);
        h12 = (TextView)findViewById(R.id.mhnov);
        h13 = (TextView)findViewById(R.id.mhstreetv);
        h14 = (TextView)findViewById(R.id.mhlandv);
        h15 = (TextView)findViewById(R.id.mhpincv);
        h16 = (TextView)findViewById(R.id.mhcityv);
        btn5 = (Button)findViewById(R.id.mhbtnchatu);

        Intent i = this.getIntent();
        midh = i.getExtras().getString("MyHomeID");

        mref = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mref.getReference();
        Query query = databaseReference.child("Home Details");
        query.orderByChild("uidhome").equalTo(midh).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PostHousedetails postHousedetails = dataSnapshot1.getValue(PostHousedetails.class);
                    h1.setText(postHousedetails.getRoomoption());
                    h2.setText(postHousedetails.getBathroomoption());
                    h3.setText(postHousedetails.getBalconyoption());
                    h4.setText(postHousedetails.getFloor());
                    h5.setText(postHousedetails.getFurnishingoption());
                    h6.setText(postHousedetails.getCarpatarea());
                    h7.setText(postHousedetails.getSuperarea());
                    h8.setText(postHousedetails.getRent());
                    h9.setText(postHousedetails.getOtherexpense());
                    h10.setText(postHousedetails.getSecurityamount());
                    h11.setText(postHousedetails.getMaintenancecharge());
                    h12.setText(postHousedetails.getHouseno());
                    h13.setText(postHousedetails.getStreetadd());
                    h14.setText(postHousedetails.getLandmark());
                    h15.setText(postHousedetails.getHpincode());
                    h16.setText(postHousedetails.getHcity());

                    Picasso.get().load(postHousedetails.getImgurl()).into(iv1);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(view);
            }
        });

    }
    private void removedata(){
        DatabaseReference mydata = mref.getReference();
        Query query1 = mydata.child("Home Details");
        query1.orderByChild("uidhome").equalTo(midh).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren())
                {
                    dataSnapshot2.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void open(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to DELETE your posr?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                removedata();
                Toast.makeText(mcchomedetails.this, "Successfully Deleted Your post", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mcchomedetails.this,MyPost.class));
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
