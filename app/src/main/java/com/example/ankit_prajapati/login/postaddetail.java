package com.example.ankit_prajapati.login;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class postaddetail extends AppCompatActivity {

    Spinner sp1;
    ArrayAdapter ar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RadioGroup rag1,rag2,rag3,rag4;
    RadioButton ragroomoption,ragbathroomsoption,ragbalconyoption,ragfurnishingoption;
    Button btnSubmit;
    ImageButton himageSelect1,himageSelect2,himageSelect3,himageSelect4;
    Uri hImageuri = null ,hImageuri2 = null,hImageuri3 = null,hImageuri4 = null;
    StorageReference storageReference,mref;
    private static final int hGALLERY_REQUEST = 1;
    private static final int hGALLERY_REQUEST2 = 2;
    private static final int hGALLERY_REQUEST3 = 3;
    private static final int hGALLERY_REQUEST4 = 4;
    String[] floorarry = {"GROUND Floor","1", "2", "3", "4","5 and Above"};
    EditText et1,et2,edt1,edt2,edt3,edt4,ad1,ad2,ad3,ad4,ad5;
    String username,uhname;
    public static final String fb_storage_path = "Homeimages/";
    String imgurl;
    String floor,roomoption,bathrooomoption,balconyoption,furnishingoption,uidhome,carpetarea,superarea,rent,otherexpense,securityamount,maintenancecharge,houseno,streetadd,landmark,hpincode,hcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postaddetail);



        rag1 = (RadioGroup) findViewById(R.id.rag1);
        rag2 = (RadioGroup) findViewById(R.id.rag2);
        rag3 = (RadioGroup) findViewById(R.id.rag3);
        rag4 = (RadioGroup) findViewById(R.id.rag4);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        ad1 = (EditText)findViewById(R.id.ad1);
        ad2 = (EditText)findViewById(R.id.ad2);
        ad3 = (EditText)findViewById(R.id.ad3);
        ad4 = (EditText)findViewById(R.id.ad4);
        ad5 = (EditText)findViewById(R.id.ad5);

        himageSelect1 = (ImageButton) findViewById(R.id.imageSelect1);
        himageSelect2 = (ImageButton) findViewById(R.id.imageSelect2);
        himageSelect3 = (ImageButton) findViewById(R.id.imageSelect3);
        himageSelect4 = (ImageButton) findViewById(R.id.imageSelect4);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());

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

        sp1 = findViewById(R.id.sp1);
        ar = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, floorarry);
        sp1.setAdapter(ar);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                floor = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        rag1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ragroomoption = rag1.findViewById(i);
                switch (i)
                {
                    case R.id.rab1:
                        roomoption = ragroomoption.getText().toString();
                        break;
                    case R.id.rab2:
                        roomoption = ragroomoption.getText().toString();
                        break;
                    case R.id.rab3:
                        roomoption = ragroomoption.getText().toString();
                        break;
                    case R.id.rab4:
                        roomoption = ragroomoption.getText().toString();
                        break;
                }
            }
        });
        rag2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ragbathroomsoption = rag2.findViewById(i);
                switch (i)
                {
                    case R.id.rbb1:
                        bathrooomoption = ragbathroomsoption.getText().toString();
                        break;
                    case R.id.rbb2:
                        bathrooomoption = ragbathroomsoption.getText().toString();
                        break;
                    case R.id.rbb3:
                        bathrooomoption = ragbathroomsoption.getText().toString();
                        break;
                    case R.id.rbb4:
                        bathrooomoption = ragbathroomsoption.getText().toString();
                        break;
                }
            }
        });
        rag3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ragbalconyoption = rag3.findViewById(i);
                switch (i)
                {
                    case R.id.rgb1:
                        balconyoption = ragbalconyoption.getText().toString();
                        break;
                    case R.id.rgb2:
                        balconyoption = ragbalconyoption.getText().toString();
                        break;
                    case R.id.rgb3:
                        balconyoption = ragbalconyoption.getText().toString();
                        break;
                    case R.id.rgb4:
                        balconyoption = ragbalconyoption.getText().toString();
                        break;
                }
            }
        });
        rag4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ragfurnishingoption = rag4.findViewById(i);
                switch (i)
                {
                    case R.id.fur1:
                        furnishingoption = ragfurnishingoption.getText().toString();
                        break;
                    case R.id.fur2:
                        furnishingoption = ragfurnishingoption.getText().toString();
                        break;
                    case R.id.fur3:
                        furnishingoption = ragfurnishingoption.getText().toString();
                        break;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                {
                open(view);
            }
                else
            {
                Toast.makeText(postaddetail.this,"Error in PostUploading",Toast.LENGTH_SHORT).show();
            }
            }
        });
        himageSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, hGALLERY_REQUEST);
            }
        });
        himageSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, hGALLERY_REQUEST2);
            }
        });

        himageSelect3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, hGALLERY_REQUEST3);
            }
        });

        himageSelect4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, hGALLERY_REQUEST4);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == hGALLERY_REQUEST && resultCode == RESULT_OK){

            hImageuri = data.getData();


            himageSelect1.setImageURI(hImageuri);

        }

        if(requestCode == hGALLERY_REQUEST2 && resultCode == RESULT_OK){


            hImageuri2 = data.getData();


            himageSelect2.setImageURI(hImageuri2);

        }

        if(requestCode == hGALLERY_REQUEST3 && resultCode == RESULT_OK){



            hImageuri3 = data.getData();

            himageSelect3.setImageURI(hImageuri3);
        }
        if(requestCode == hGALLERY_REQUEST4 && resultCode == RESULT_OK){



            hImageuri4 = data.getData();

            himageSelect4.setImageURI(hImageuri4);
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver =getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private Boolean validate(){
        Boolean result =false;

        carpetarea = et1.getText().toString();
        superarea = et2.getText().toString();
        rent = edt1.getText().toString();
        otherexpense = edt2.getText().toString();
        securityamount = edt3.getText().toString();
        maintenancecharge = edt4.getText().toString();
        houseno = ad1.getText().toString();
        streetadd = ad2.getText().toString();
        landmark = ad3.getText().toString();
        hpincode = ad4.getText().toString();
        hcity = ad5.getText().toString();

        if(rag1.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this,"Please Select No. of Rooms",Toast.LENGTH_SHORT).show();
        }
        else if(rag2.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Select No. of Bathrooms", Toast.LENGTH_SHORT).show();
        }
        else if(rag3.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Select No. of Balcony", Toast.LENGTH_SHORT).show();
        }
        else if(floor.equals("")){
            Toast.makeText(this, "Please Select Floor", Toast.LENGTH_SHORT).show();
        }
        else if(rag4.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(this, "Please Select Furnishing", Toast.LENGTH_SHORT).show();
        }
        else if(carpetarea.isEmpty())
        {
            Toast.makeText(this, "Please Enter Carpet Area", Toast.LENGTH_SHORT).show();
        }
        else if(superarea.isEmpty())
        {
            Toast.makeText(this, "Please Enter Super Area", Toast.LENGTH_SHORT).show();
        }
        else if(rent.isEmpty())
        {
            Toast.makeText(this, "Please Enter Monthly Rent", Toast.LENGTH_SHORT).show();
        }
        else if(rent.length()>5)
        {
            Toast.makeText(this, "Please Enter Rent Amount Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(otherexpense.isEmpty())
        {
            Toast.makeText(this, "Please Enter Other Expenses", Toast.LENGTH_SHORT).show();
        }
        else if(otherexpense.length()>5)
        {
            Toast.makeText(this, "Please Enter Other Expenses Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(securityamount.isEmpty())
        {
            Toast.makeText(this, "Please Enter Security Amount", Toast.LENGTH_SHORT).show();
        }
        else if(securityamount.length()>5)
        {
            Toast.makeText(this, "Please Enter Security Amount Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(maintenancecharge.isEmpty() )
        {
            Toast.makeText(this, "Please Enter Maintanance Charges", Toast.LENGTH_SHORT).show();
        }
        else if(maintenancecharge.length()>5)
        {
            Toast.makeText(this, "Please Enter Maintanance Under 5 Digits", Toast.LENGTH_SHORT).show();
        }
        else if(houseno.isEmpty())
        {
            Toast.makeText(this, "Please Enter House Number", Toast.LENGTH_SHORT).show();
        }
        else if(streetadd.isEmpty())
        {
            Toast.makeText(this, "Please Enter Street Address", Toast.LENGTH_SHORT).show();
        }
        else if(landmark.isEmpty()){
            Toast.makeText(this, "Please Enter Landmark", Toast.LENGTH_SHORT).show();
        }
        else if(hpincode.isEmpty()){
            Toast.makeText(this, "Please Enter Pincode", Toast.LENGTH_SHORT).show();
        }
        else if(hcity.isEmpty())
        {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
        }
        else if(hImageuri==null){
            Toast.makeText(this, "Please Insert Image1", Toast.LENGTH_SHORT).show();
        }
        else if(hImageuri2==null){
            Toast.makeText(this, "Please Insert Image2", Toast.LENGTH_SHORT).show();
        }
        else if(hImageuri3==null){
            Toast.makeText(this, "Please Insert Image3", Toast.LENGTH_SHORT).show();
        }
        else if(hImageuri4==null){
            Toast.makeText(this, "Please Insert Image4", Toast.LENGTH_SHORT).show();
        }
        else
        {
            result = true;
        }
        return result;
    }

    private void sendhousedetails() {

        uhname = username;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference().child("Home Details");
        uidhome = myRef.push().getKey();
        final StorageReference imageReference = storageReference.child(fb_storage_path+System.currentTimeMillis()+"."+getImageExt(hImageuri));
        imageReference.putFile(hImageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgurl = uri.toString();
                        PostHousedetails postHousedetails = new PostHousedetails(roomoption,bathrooomoption,balconyoption,floor,furnishingoption,carpetarea,superarea,rent,otherexpense,securityamount,maintenancecharge,houseno,streetadd,landmark,hpincode,hcity,uhname,uidhome,imgurl);
                        myRef.child(uidhome).setValue(postHousedetails);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(postaddetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        StorageReference imageReference2 = storageReference.child("Home Details");
        imageReference2.child(uidhome).child("h2 Image").putFile(hImageuri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        StorageReference imageReference3 = storageReference.child("Home Details");
        imageReference3.child(uidhome).child("h3 Image").putFile(hImageuri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        StorageReference imageReference4 = storageReference.child("Home Details");
        imageReference4.child(uidhome).child("h4 Image").putFile(hImageuri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }
    public void open(View view) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your Ad will be Posted, Continue ?"
        );
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                    sendhousedetails();
                    Toast.makeText(postaddetail.this,"Successfully Add Posted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(postaddetail.this,Main2Activity.class));
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