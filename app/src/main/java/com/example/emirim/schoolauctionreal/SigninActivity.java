package com.example.emirim.schoolauctionreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emirim.schoolauctionreal.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class SigninActivity extends BaseActivity {
    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    private EditText idField;
    private EditText passwordField;
    private EditText nameField;
    private EditText schoolField;
    private EditText phoneField;
    private Button okbutton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        idField = (EditText)findViewById(R.id.signin_activity_id_field);
        passwordField = (EditText)findViewById(R.id.signin_activity_password_field);
        nameField = (EditText)findViewById(R.id.signin_activity_name_field);
        schoolField = (EditText)findViewById(R.id.signin_activity_school_field);
        phoneField = (EditText)findViewById(R.id.signin_activity_phone_field);
        okbutton = (Button)findViewById(R.id.ok_button);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        /*
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFieldNotEmpty(idField) && isFieldNotEmpty(passwordField) && isFieldNotEmpty(nameField) && isFieldNotEmpty(schoolField) && isFieldNotEmpty(phoneField)) {
                    SignIn model = new SignIn();
                    model.id = getFieldValue(idField);
                    model.password = getFieldValue(passwordField);
                    model.name = getFieldValue(nameField);
                    model.school = getFieldValue(schoolField);
                    model.phone = getFieldValue(phoneField);

                    API a = APIRequestManager.getInstance().prepareAPIRequest();
                    Call<SignInResponse> apiCall = a.signIn(model);

                    apiCall.enqueue(new Callback<SignInResponse>() {
                        @Override
                        public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                            if(response.code() == 200) {
                                Toast.makeText(SigninActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<SignInResponse> call, Throwable t) {
                            Toast.makeText(SigninActivity.this, "회원가입을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        */
    }
    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = idField.getText().toString();
        String password = passwordField.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SigninActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        String name = nameField.getText().toString();
        String phone = phoneField.getText().toString().trim().replace("-","");
        String school =schoolField.getText().toString();

        // Write new user
        writeNewUser(user.getUid(),name , user.getEmail(),school,phone);

        // Go to MainActivity
        startActivity(new Intent(SigninActivity.this, MainActivity.class));
        finish();

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(idField.getText().toString())) {
            idField.setError("Required");
            result = false;
        } else {
            passwordField.setError(null);
        }

        if (TextUtils.isEmpty(passwordField.getText().toString())) {
            passwordField.setError("Required");
            result = false;
        } else {
            passwordField.setError(null);
        }
        if (TextUtils.isEmpty(nameField.getText().toString()) || TextUtils.isEmpty(schoolField.getText().toString())
                || TextUtils.isEmpty(phoneField.getText().toString())) {
            Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_SHORT).show();
            return false;
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String id,String school,String phone) {
        User user = new User(id , name, school,phone);

        mDatabase.child("users").child(userId).setValue(user);
    }

    /*
    private boolean isFieldNotEmpty(EditText e) {
        return e.getText().length() > 0;
    }

    private String getFieldValue(EditText e) {
        return e.getText().toString();
    }
    */
}
