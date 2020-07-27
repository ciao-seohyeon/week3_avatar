package com.example.week3_avatar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.week3_avatar.Retrofit.IMyService;
import com.example.week3_avatar.Retrofit.RetrofitClient;
import com.example.week3_avatar.Retrofit.User;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.io.IOException;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edt_login_email, edt_login_password;
    Button btn_login;
    TextView txt_create_account;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final IMyService retrofitClient = RetrofitClient.getApiService();

        edt_login_email = findViewById(R.id.edt_email);
        edt_login_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edt_login_email.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Please write your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edt_login_password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Please write your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String id = edt_login_email.getText().toString();
                final String password = edt_login_password.getText().toString();
                final String[] realpassword = {""};
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        Call<User> tryUser = retrofitClient.getUser(id);
                        try {
                            Response<User> res = tryUser.execute();
                            if (res.body() != null){
                                realpassword[0] = res.body().getPassword();
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (realpassword[0].equals(password)) {
                                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            i.putExtra("id", id);
                                            startActivity(i);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Wrong Password or ID", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            //////////////////////////////////////////////////////////////
        });

        ////////////////////////////////////////////////// registeration ////////////////////////////////////////////////
        txt_create_account = findViewById(R.id.txt_create_account);
        txt_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View register_layout = LayoutInflater.from(LoginActivity.this).inflate(R.layout.register_layout, null);
                new MaterialStyledDialog.Builder(LoginActivity.this)
                        .setTitle("Registeration")
                        //.setIcon(R.drawable.logo)
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCLE")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                final EditText edt_register_email = register_layout.findViewById(R.id.edt_email);
                                final EditText edt_register_name = register_layout.findViewById(R.id.edt_name);
                                final EditText edt_register_password = register_layout.findViewById(R.id.edt_password);

                                if (TextUtils.isEmpty(edt_register_email.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Email cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(edt_register_name.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Name cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (TextUtils.isEmpty(edt_register_password.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Call<User> createFaceBook = retrofitClient.createUser(new User(edt_register_name.getText().toString(),  edt_register_email.getText().toString(),  edt_register_password.getText().toString()));
                                        System.out.println("Email is "+edt_register_email.getText().toString());
                                        try {
                                            createFaceBook.execute();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        }).show();
            }
        });
    }




}
