package com.example.ankit_prajapati.login;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class cchomedetails extends AppCompatActivity {

    TextView h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16;
    ImageView iv1;
    String idh,mo_no;
    FirebaseDatabase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cchomedetails);

        iv1 = (ImageView)findViewById(R.id.hiv);
        h1 = (TextView)findViewById(R.id.hroomv);
        h2 = (TextView)findViewById(R.id.hbathv);
        h3 = (TextView)findViewById(R.id.hbalv);
        h4 = (TextView)findViewById(R.id.hfloorv);
        h5 = (TextView)findViewById(R.id.hfurn5);
        h6 = (TextView)findViewById(R.id.hcarv);
        h7 = (TextView)findViewById(R.id.hsupv);
        h8 = (TextView)findViewById(R.id.hrentv);
        h9 = (TextView)findViewById(R.id.hotherv);
        h10 = (TextView)findViewById(R.id.hsecv);
        h11 = (TextView)findViewById(R.id.hmainv);
        h12 = (TextView)findViewById(R.id.hnov);
        h13 = (TextView)findViewById(R.id.hstreetv);
        h14 = (TextView)findViewById(R.id.hlandv);
        h15 = (TextView)findViewById(R.id.hpincv);
        h16 = (TextView)findViewById(R.id.hcityv);

        Intent i = this.getIntent();
        idh = i.getExtras().getString("HomeID");

        mref = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mref.getReference();
        Query query = databaseReference.child("Home Details");
        query.orderByChild("uidhome").equalTo(idh).addListenerForSingleValueEvent(new ValueEventListener() {
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

    }
}
