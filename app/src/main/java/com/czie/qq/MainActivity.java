package com.czie.qq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText userNameEditText, passwordEditText;
    private ImageButton loginButton;
    private ShareHelper shareHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        shareHelper=new ShareHelper(context);
        initview();
    }

    private void initview() {
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行登录页面的处理
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.length() > 0) {
                    if (username.equals("zk")) {
                        if (password.length() > 0) {
                            if (password.equals("123")) {
                                // 对账号和密码进行保存
                                shareHelper.save("username",username);
                                shareHelper.save("password",password);
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "密码不正确", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"请填写密码",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this,"用户名不正确",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"请填写用户名",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userNameEditText.setText(shareHelper.read("username"));
        passwordEditText.setText(shareHelper.read("password"));

    }
}