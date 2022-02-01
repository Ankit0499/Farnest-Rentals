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

public class mccshopdetails extends AppCompatActivity {

    TextView sh1,sh2,sh3,sh4,sh5,sh6,sh7,sh8,sh9,sh10,sh11,sh12,sh13,sh14,sh15,sh16;
    ImageView siv1;
    String sidh;
    FirebaseDatabase msref;
    Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mccshopdetails);

        siv1 = (ImageView)findViewById(R.id.msiv);
        sh1 = (TextView)findViewById(R.id.msroomv);
        sh2 = (TextView)findViewById(R.id.msbathv);
        sh3 = (TextView)findViewById(R.id.msbalv);
        sh4 = (TextView)findViewById(R.id.msfloorv);
        sh5 = (TextView)findViewById(R.id.msfurn5);
        sh6 = (TextView)findViewById(R.id.mscarv);
        sh7 = (TextView)findViewById(R.id.mssupv);
        sh8 = (TextView)findViewById(R.id.msrentv);
        sh9 = (TextView)findViewById(R.id.msotherv);
        sh10 = (TextView)findViewById(R.id.mssecv);
        sh11 = (TextView)findViewById(R.id.msmainv);
        sh12 = (TextView)findViewById(R.id.msnov);
        sh13 = (TextView)findViewById(R.id.msstreetv);
        sh14 = (TextView)findViewById(R.id.mslandv);
        sh15 = (TextView)findViewById(R.id.mspincv);
        sh16 = (TextView)findViewById(R.id.mscityv);
        btn6 = (Button)findViewById(R.id.sbtnchatus);

        Intent i = this.getIntent();
        sidh = i.getExtras().getString("MyShopID");

        msref = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = msref.getReference();
        Query query = databaseReference.child("Shop Details");
        query.orderByChild("uidshop").equalTo(sidh).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    PostShopdetails postShopdetails = dataSnapshot1.getValue(PostShopdetails.class);
                    sh1.setText(postShopdetails.getWroption());
                    sh2.setText(postShopdetails.getCsoption());
                    sh3.setText(postShopdetails.getRsoption());
                    sh4.setText(postShopdetails.getFloorlocation());
                    sh5.setText(postShopdetails.getFoption());
                    sh6.setText(postShopdetails.getScarpatarea());
                    sh7.setText(postShopdetails.getSsuperarea());
                    sh8.setText(postShopdetails.getSrent());
                    sh9.setText(postShopdetails.getSotherexpense());
                    sh10.setText(postShopdetails.getSsecurityamount());
                    sh11.setText(postShopdetails.getSmaintenancecharge());
                    sh12.setText(postShopdetails.getSshopno());
                    sh13.setText(postShopdetails.getSstreet());
                    sh14.setText(postShopdetails.getSlandmark());
                    sh15.setText(postShopdetails.getSpincode());
                    sh16.setText(postShopdetails.getScity());

                    Picasso.get().load(postShopdetails.getSimgurl()).fit().into(siv1);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(view);
            }
        });

    }
    private void removedata(){
        DatabaseReference mydata = msref.getReference();
        Query query1 = mydata.child("Shop Details");
        query1.orderByChild("uidshop").equalTo(sidh).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Toast.makeText(mccshopdetails.this,"Successfully Deleted Your Posted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mccshopdetails.this,Myshop.class));
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
