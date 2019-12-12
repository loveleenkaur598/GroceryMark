package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.grocerymark.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText edtPassword,edtEmail;
    Button btnSignIn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignIn.this, "Please enter the email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignIn.this, "Please enter the password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() <= 6){
                    Toast.makeText(SignIn.this, "Password must be >= 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }


                mDialog.setMessage("Please waiting.....");
                mDialog.show();

                firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignIn.this, "Sign in Successfully !", Toast.LENGTH_SHORT).show();
                            Intent homeScreen = new Intent(SignIn.this,HomeScreen.class);
                            startActivity(homeScreen);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                table_user.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        //Check If user doesnot exist in database
//                        mDialog.dismiss();
//                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
//                            //Get user information
//
//                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
//
//                            if (user.getPassword().equals(edtPassword.getText().toString())) {
//
//                                //Toast.makeText(SignIn.this, "Sign in Successfully !", Toast.LENGTH_SHORT).show();
//                                Intent homeScreen = new Intent(SignIn.this,HomeScreen.class);
//                                startActivity(homeScreen);
//                                finish();
//
//
//                            } else {
//
//                                Toast.makeText(SignIn.this, "Wrong password !!!", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }else{
//                            Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
            }
        });
    }
}
