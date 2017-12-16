package com.example.dodo1305.schoolauctionreal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dodo1305.schoolauctionreal.api.API;
import com.example.dodo1305.schoolauctionreal.api.APIRequestManager;
import com.example.dodo1305.schoolauctionreal.model.SignIn;
import com.example.dodo1305.schoolauctionreal.model.SignInResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class SigninActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;

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
    }

    private boolean isFieldNotEmpty(EditText e) {
        return e.getText().length() > 0;
    }

    private String getFieldValue(EditText e) {
        return e.getText().toString();
    }
}
