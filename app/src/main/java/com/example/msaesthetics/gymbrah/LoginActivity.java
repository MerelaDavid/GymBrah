package com.example.msaesthetics.gymbrah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;


public class LoginActivity extends AppCompatActivity  {
    private EditText UserEmail,password;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserEmail = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passwd);
        login= (TextView) findViewById(R.id.textView7);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate() && BaseValidate(password.getText().toString(),UserEmail.getText().toString())){
                    //check data base then login in
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    private Boolean Validate(){
        Boolean resoult = false;
        String email = UserEmail.getText().toString();
        String passwd = password.getText().toString();

        if (email.isEmpty() || passwd.isEmpty()){
            resoult = false;
            Toast.makeText(this, "Please enter all details",Toast.LENGTH_SHORT);
        }
        else {
            resoult = true;
        }
        return resoult;
    }
    private Boolean BaseValidate(String pasword, String Email){
        Connection con;
        Boolean account = false;
        Boolean Passwd;
        Boolean email;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7253133","sql7253133","N7nu7IsM7w");
            Passwd = DataBaseConnection.UsersPasswdCheck(con,pasword,Email);
            email = DataBaseConnection.UsersEmailCheck(con, Email);
            con.close();
            if(Passwd&&email){
                account = true;
            }
        }catch (Exception e){
            System.out.println("User login error" + e.getMessage());
        }
        return account;
    }


}
