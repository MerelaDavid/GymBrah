package com.example.msaesthetics.gymbrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;

public class SignUpActivity extends AppCompatActivity {
    private EditText Username;
    private EditText Email;
    private EditText Passwd0;
    private EditText Passwd1;
    private TextView Signup;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Username = (EditText) findViewById(R.id.Username);
        Email = (EditText) findViewById(R.id.Email);
        Passwd0 = (EditText) findViewById(R.id.Password1);
        Passwd1 = (EditText) findViewById(R.id.Password2);
        Signup = (TextView) findViewById(R.id.SignUp);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7253133","sql7253133","N7nu7IsM7w");
                    if(passwordCheck(Passwd0.getText().toString(),Passwd1.getText().toString())&&DataBaseConnection.UsersEmailCheck(con,Email.getText().toString())&& DataBaseConnection.UserNameCheck(con,Username.getText().toString())){
                        int UserId= DataBaseConnection.MaxUserId(con);
                        DataBaseConnection.UsserSignUp(con,UserId,Username.getText().toString(),Email.getText().toString(),Passwd0.getText().toString());
                        con.close();
                        Intent intent = new Intent(getApplicationContext(),GymSetUpActivity.class);
                        startActivity(intent);
                    }

                }catch (Exception e){
                    System.out.println("Signup error : " + e.getMessage());
                }

            }
        });
    }

    private Boolean passwordCheck(String passwd0, String passwd1){
        Boolean Validate = false;
        if(passwd0==passwd1){
            Validate= true;
        }
        return Validate;
    }
}
