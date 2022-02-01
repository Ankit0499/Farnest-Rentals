package com.example.ankit_prajapati.login;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;

public class postshopd extends AppCompatActivity {

    Spinner spr1;
    RadioGroup rg1,rg2,rg3,rg4;
    ArrayAdapter ar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RadioButton rgwroption,rgcsoption,rgrsoption,rgfoption;
    Button btnshop1;
    String username,usname,simgurl;
    ImageButton simageSelect1,simageSelect2,simageSelect3,simageSelect4;
    Uri sImageuri = null ,sImageuri2 = null,sImageuri3 = null,sImageuri4 = null;
    StorageReference storageReference;
    private static final int SGALLERY_REQUEST = 1;
    private static final int SGALLERY_REQUEST2 = 2;
    private static final int SGALLERY_REQUEST3 = 3;
    private static final int SGALLERY_REQUEST4 = 4;
    public static final String fb_storage_path = "Shopimages/";
    EditText ett1,ett2,edtt1,edtt2,edtt3,edtt4,ads1,ads2,ads3,ads4,ads5;
    String[] floorloc = {"GROUND Floor","1", "2", "3", "4","5 and Above"};
    String wroption,csoption,rsoption,floorlocation,foption,uidshop,scarpetarea,ssuperarea,srent,sotherexpense,ssecurityamount,smaintenancecharge,shouseno,sstreetadd,slandmark,shpincode,shcity;

    String posttype = "shoptype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postshopd);

        rg1 = (RadioGroup)findViewById(R.id.rg1);
        rg2 = (RadioGroup)findViewById(R.id.rg2);
        rg3 = (RadioGroup)findViewById(R.id.rg3);
        rg4 = (RadioGroup)findViewById(R.id.rg4);
        btnshop1 = (Button)findViewById(R.id.btnshop1);
        ett1 = (EditText)findViewById(R.id.ett1);
        ett2 = (EditText)findViewById(R.id.ett2);
        edtt1 = (EditText)findViewById(R.id.edtt1);
        edtt2 = (EditText)findViewById(R.id.edtt2);
        edtt3 = (EditText)findViewById(R.id.edtt3);
        edtt4 = (EditText)findViewById(R.id.edtt4);
        ads1 = (EditText) findViewById(R.id.ads1);
        ads2 = (EditText) findViewById(R.id.ads2);
        ads3 = (EditText) findViewById(R.id.ads3);
        ads4 = (EditText) findViewById(R.id.ads4);
        ads5 = (EditText) findViewById(R.id.ads5);

        simageSelect1 = (ImageButton)findViewById(R.id.simageSelect1);
        simageSelect2 = (ImageButton)findViewById(R.id.simageSelect2);
        simageSelect3 = (ImageButton)findViewById(R.id.simageSelect3);
        simageSelect4 = (ImageButton)findViewById(R.id.simageSelect4);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile= dataSnapshot.getValue(UserProfile.class);
                username = userProfile.name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spr1 = findViewById(R.id.spr1);
        ar = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, floorloc);
        spr1.setAdapter(ar);

        spr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floorlocation = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rgwroption = rg1.findViewById(i);
                switch (i)
                {
                    case R.id.rb1:
                        wroption = rgwroption.getText().toString();
                    case R.id.rb2:
                        wroption = rgwroption.getText().toString();
                }
            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rgcsoption = rg2.findViewById(i);
                switch (i)
                {
                    case R.id.rb3:
                        csoption = rgcsoption.getText().toString();
                    case R.id.rb4:
                        csoption = rgcsoption.getText().toString();
                }
            }
        });

        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rgrsoption = rg3.findViewById(i);
                switch (i)
                {
                    case R.id.rb5:
                        rsoption = rgrsoption.getText().toString();
                    case R.id.rb6:
                        rsoption = rgrsoption.getText().toString();
                }
            }
        });

        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rgfoption = rg4.findViewById(i);
                switch (i)
                {
                    case R.id.rb7:
                        foption = rgfoption.getText().toString();
                    case R.id.rb8:
                        foption = rgfoption.getText().toString();
                    case R.id.rb9:
                        foption = rgfoption.getText().toString();
                }
            }
        });
        btnshop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    open(view);
                }
                else{
                    Toast.makeText(postshopd.this,"Can't Upload your Post",Toast.LENGTH_SHORT).show();
                }
            }
        });

        simageSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, SGALLERY_REQUEST);
            }
        });
        simageSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, SGALLERY_REQUEST2);
            }
        });

        simageSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, SGALLERY_REQUEST3);
            }
        });

        simageSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, SGALLERY_REQUEST4);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SGALLERY_REQUEST && resultCode == RESULT_OK){

            sImageuri = data.getData();


            simageSelect1.setImageURI(sImageuri);

        }

        if(requestCode == SGALLERY_REQUEST2 && resultCode == RESULT_OK){


            sImageuri2 = data.getData();


            simageSelect2.setImageURI(sImageuri2);

        }

        if(requestCode == SGALLERY_REQUEST3 && resultCode == RESULT_OK){



            sImageuri3 = data.getData();

            simageSelect3.setImageURI(sImageuri3);
        }
        if(requestCode == SGALLERY_REQUEST4 && resultCode == RESULT_OK){



            sImageuri4 = data.getData();

            simageSelect4.setImageURI(sImageuri4);
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private Boolean validate()
    {
        Boolean result = false;

        scarpetarea = ett1.getText().toString();
        ssuperarea = ett2.getText().toString();
        srent = edtt1.getText().toString();
        sotherexpense = edtt2.getText().toString();
        ssecurityamount = edtt3.getText().toString();
        smaintenancecharge = edtt4.getText().toString();
        shouseno = ads1.getText().toString();
        sstreetadd = ads2.getText().toString();
        slandmark = ads3.getText().toString();
        shpincode = ads4.getText().toString();
        shcity = ads5.getText().toString();

        if(rg1.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this,"Please Answer Personal Washroom",Toast.LENGTH_SHORT).show();
        }
        else if(rg2.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Answer Corner Shop", Toast.LENGTH_SHORT).show();
        }
        else if(rg3.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Answer MainRoad Facing", Toast.LENGTH_SHORT).show();
        }
        else if(floorlocation.equals("")){
            Toast.makeText(this, "Please Select Floor", Toast.LENGTH_SHORT).show();
        }
        else if(rg4.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Select Furnishing", Toast.LENGTH_SHORT).show();
        }
        else if(scarpetarea.isEmpty())
        {
            Toast.makeText(this, "Please Enter Carpet Area", Toast.LENGTH_SHORT).show();
        }
        else if(scarpetarea.isEmpty())
        {
            Toast.makeText(this, "Please Enter Super Area", Toast.LENGTH_SHORT).show();
        }
        else if(srent.isEmpty())
        {
            Toast.makeText(this, "Please Enter Monthly Rent", Toast.LENGTH_SHORT).show();
        }
        else if(srent.length()>5)
        {
            Toast.makeText(this, "Please Enter Rent Amount Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(sotherexpense.isEmpty())
        {
            Toast.makeText(this, "Please Enter Other Expenses", Toast.LENGTH_SHORT).show();
        }
        else if(sotherexpense.length()>5)
        {
            Toast.makeText(this, "Please Enter Other Expenses Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(ssecurityamount.isEmpty())
        {
            Toast.makeText(this, "Please Enter Security Amount", Toast.LENGTH_SHORT).show();
        }
        else if(ssecurityamount.length()>5)
        {
            Toast.makeText(this, "Please Enter Security Amount Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(smaintenancecharge.isEmpty() )
        {
            Toast.makeText(this, "Please Enter Maintanance Charges", Toast.LENGTH_SHORT).show();
        }
        else if(smaintenancecharge.length()>5)
        {
            Toast.makeText(this, "Please Enter Maintanance Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(shouseno.isEmpty())
        {
            Toast.makeText(this, "Please Enter House Number", Toast.LENGTH_SHORT).show();
        }
        else if(sstreetadd.isEmpty())
        {
            Toast.makeText(this, "Please Enter Street Address", Toast.LENGTH_SHORT).show();
        }
        else if(slandmark.isEmpty()){
            Toast.makeText(this, "Please Enter Landmark", Toast.LENGTH_SHORT).show();
        }
        else if(shpincode.isEmpty()){
            Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();
        }
        else if(shcity.isEmpty())
        {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
        }
        else if(sImageuri==null){
            Toast.makeText(this, "Please Insert Image1", Toast.LENGTH_SHORT).show();
        }
        else if(sImageuri2==null){
            Toast.makeText(this, "Please Insert Image2", Toast.LENGTH_SHORT).show();
        }
        else if(sImageuri3==null){
            Toast.makeText(this, "Please Insert Image3", Toast.LENGTH_SHORT).show();
        }
        else if(sImageuri4==null){
            Toast.makeText(this, "Please Insert Image4", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;

        }
        return result;
    }
    private void sendshopdetails(){

        usname = username;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference().child("Shop Details");
        uidshop = myRef.push().getKey();
        final StorageReference imageReference = storageReference.child(fb_storage_path+System.currentTimeMillis()+"."+getImageExt(sImageuri));
        imageReference.putFile(sImageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        simgurl = uri.toString();
                        PostShopdetails postshopdetails = new PostShopdetails(wroption, csoption, rsoption, floorlocation, foption, scarpetarea, ssuperarea, srent, sotherexpense, ssecurityamount, smaintenancecharge, shouseno, sstreetadd, slandmark, shpincode, shcity, posttype, usname,uidshop, simgurl);
                        myRef.child(uidshop).setValue(postshopdetails);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(postshopd.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
                StorageReference imageReference2 = storageReference.child("Shop Images");
                imageReference2.child(uidshop).child("S2 Image").putFile(sImageuri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
                StorageReference imageReference3 = storageReference.child("Shop Images");
                imageReference3.child(uidshop).child("S3 Image").putFile(sImageuri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
                StorageReference imageReference4 = storageReference.child("Shop Images");
                imageReference4.child(uidshop).child("S4 Image").putFile(sImageuri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

    }
    public void open(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your Ad will be Posted, Continue ?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                sendshopdetails();
                Toast.makeText(postshopd.this,"Successfully Add Posted",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(postshopd.this,Main2Activity.class));
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
