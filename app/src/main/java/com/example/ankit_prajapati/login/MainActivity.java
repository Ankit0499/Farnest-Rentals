package com.example.ankit_prajapati.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView  Register;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Name = (EditText)findViewById(R.id.etname);
        Password = (EditText)findViewById(R.id.etpassword);
        Login = (Button)findViewById(R.id.btnlogin);
        Register = (TextView)findViewById(R.id.tvregister);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user !=null){
            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }

        progressDialog = new ProgressDialog(this);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        validate(Name.getText().toString(), Password.getText().toString());

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });
    }

    private void validate(String userName, String userPassword){

        if(userName.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
        }
        else if(userPassword.isEmpty())
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{

        progressDialog.setMessage("waiting for response untill you are verified");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailverification();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Enter Valid Email or Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        }
    }
    private void checkEmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
        else{
            Toast.makeText(this,"Please Verify your Email!!..",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}