package com.example.grocerymark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class SignUp extends AppCompatActivity {

    MaterialEditText edtPhone,edtName,edtPassword,edtAddress,edtEmail;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassword);
        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtAddress = (MaterialEditText)findViewById(R.id.edtAddress);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Product");

        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != "null")
//        {
//            Intent mainPage = new Intent(SignUp.this,MainActivity.class);
//            startActivity(mainPage);
//            finish();
//        }
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting.....");
                mDialog.show();

                String name = edtName.getText().toString();
                String address = edtAddress.getText().toString();
                final String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                final String password = edtPassword.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(SignUp.this, "Please enter the name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    Toast.makeText(SignUp.this, "Please enter the address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Please enter the email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(SignUp.this, "Please enter the phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Please enter the password.", Toast.LENGTH_SHORT).show();
                    return;
                }


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            //Toast.makeText(SignUp.this, "Phone Number already register !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();

                            firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                uid = task.getResult().getUser().getUid();
                                                User user = new User (edtName.getText().toString(),edtPassword.getText().toString(),
                                                edtEmail.getText().toString(),edtAddress.getText().toString(),edtPhone.getText().toString());
                                                table_user.child(uid).setValue(user);
                                                Toast.makeText(SignUp.this, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                                                Intent mainPage = new Intent(SignUp.this,MainActivity.class);
                                                startActivity(mainPage);
                                                finish();
                                            }else{
                                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
