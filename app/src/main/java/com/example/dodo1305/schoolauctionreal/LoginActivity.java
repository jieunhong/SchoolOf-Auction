package com.example.dodo1305.schoolauctionreal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dodo1305.schoolauctionreal.api.API;
import com.example.dodo1305.schoolauctionreal.api.APIRequestManager;
import com.example.dodo1305.schoolauctionreal.model.AccessToken;
import com.example.dodo1305.schoolauctionreal.model.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dodo1 on 2017-12-17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText idField;
    private EditText passwordField;
    private Button loginButton;
    private Button signInButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idField = (EditText)findViewById(R.id.id);
        passwordField = (EditText)findViewById(R.id.passwd);
        loginButton = (Button)findViewById(R.id.loginButton);
        signInButton = (Button)findViewById(R.id.loginButton2);

        mAuth=FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idField.getText().length() > 0 && passwordField.getText().length() > 0) {
                    Auth a = new Auth();
                    a.id = idField.getText().toString();
                    a.password = passwordField.getText().toString();

                    API api = APIRequestManager.getInstance().prepareAPIRequest();
                    Call<AccessToken> apiCall = api.login(a);

                    apiCall.enqueue(new Callback<AccessToken>() {
                        @Override
                        public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                            if(response.code() == 200) {
                                Log.e("APICall", new Gson().toJson(response.body()));
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccessToken> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "로그인을 실패하였습니다. - onFailures", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "입력되지 않은 칸이 있습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(signInIntent);
            }
        });
    }
}
