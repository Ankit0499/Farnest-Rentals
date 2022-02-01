package com.example.ankit_prajapati.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail, userAge;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name, mobileno, password;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagepath;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validate()) {
                        String user_email = userEmail.getText().toString().trim();
                        String user_password = userPassword.getText().toString().trim();

                        firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sentEmailverification();
                                }
                                else
                                {
                                    Toast.makeText(RegistrationActivity.this,"Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch(Exception e) {
                    Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK  && data.getData() != null){
            imagepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etUserName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin= (TextView)findViewById(R.id.tvUserLogin);
        userAge = (EditText)findViewById(R.id.etAge);
        userProfilePic = (ImageView)findViewById(R.id.ivProfile);
    }
    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        mobileno = userAge.getText().toString();
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(name.isEmpty()){
            Toast.makeText(this,"Please Enter UserName",Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty() /*||  email.matches(emailPattern)*/){
            Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else if(password.length()<8){
            Toast.makeText(this, "Please Enter 8 Digit Password", Toast.LENGTH_SHORT).show();
        }
        else if(mobileno.isEmpty()){
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
        }
        else if(mobileno.length()<10 || mobileno.length()>10){
            Toast.makeText(this, "Please Enter 10 Digit Number", Toast.LENGTH_SHORT).show();
        }
        else if(imagepath == null){
            Toast.makeText(this, "Please Add Your Profile Picture", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }
    private void sentEmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this,"Successfully Sent Verification Email!..", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this,"Verification has'nt been sent..!!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference().child("Users");
        StorageReference imageReference = storageReference.child("Users").child(firebaseAuth.getUid()).child("MyProfile");
        UploadTask uploadTask =imageReference.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this,"Profile not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this,"Profile Uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(name,email,mobileno);
        myRef.child(firebaseAuth.getUid()).setValue(userProfile);
    }
}