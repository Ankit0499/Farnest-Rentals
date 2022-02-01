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

public class ccshopdetails extends AppCompatActivity {

    TextView h1,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16;
    ImageView iv1;
    String idh;
    FirebaseDatabase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccshopdetails);

        iv1 = (ImageView)findViewById(R.id.siv);
        h1 = (TextView)findViewById(R.id.sroomv);
        h2 = (TextView)findViewById(R.id.sbathv);
        h3 = (TextView)findViewById(R.id.sbalv);
        h4 = (TextView)findViewById(R.id.sfloorv);
        h5 = (TextView)findViewById(R.id.sfurn5);
        h6 = (TextView)findViewById(R.id.scarv);
        h7 = (TextView)findViewById(R.id.ssupv);
        h8 = (TextView)findViewById(R.id.srentv);
        h9 = (TextView)findViewById(R.id.sotherv);
        h10 = (TextView)findViewById(R.id.ssecv);
        h11 = (TextView)findViewById(R.id.smainv);
        h12 = (TextView)findViewById(R.id.snov);
        h13 = (TextView)findViewById(R.id.sstreetv);
        h14 = (TextView)findViewById(R.id.slandv);
        h15 = (TextView)findViewById(R.id.spincv);
        h16 = (TextView)findViewById(R.id.scityv);

        Intent i = this.getIntent();
        idh = i.getExtras().getString("ShopID");

        mref = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mref.getReference();
        Query query = databaseReference.child("Shop Details");
        query.orderByChild("uidshop").equalTo(idh).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PostShopdetails postShopdetails = dataSnapshot1.getValue(PostShopdetails.class);
                    h1.setText(postShopdetails.getWroption());
                    h2.setText(postShopdetails.getCsoption());
                    h3.setText(postShopdetails.getRsoption());
                    h4.setText(postShopdetails.getFloorlocation());
                    h5.setText(postShopdetails.getFoption());
                    h6.setText(postShopdetails.getScarpatarea());
                    h7.setText(postShopdetails.getSsuperarea());
                    h8.setText(postShopdetails.getSrent());
                    h9.setText(postShopdetails.getSotherexpense());
                    h10.setText(postShopdetails.getSsecurityamount());
                    h11.setText(postShopdetails.getSmaintenancecharge());
                    h12.setText(postShopdetails.getSshopno());
                    h13.setText(postShopdetails.getSstreet());
                    h14.setText(postShopdetails.getSlandmark());
                    h15.setText(postShopdetails.getSpincode());
                    h16.setText(postShopdetails.getScity());

                    Picasso.get().load(postShopdetails.getSimgurl()).into(iv1);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
