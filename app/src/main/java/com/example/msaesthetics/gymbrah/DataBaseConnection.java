package com.example.msaesthetics.gymbrah;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

import javax.xml.transform.Result;

/**
 * Created by Ms Aesthetics on 24. 08. 2018.
 */

public class DataBaseConnection {


    // Password check....if pasword is the same as in the database

    static Boolean UsersPasswdCheck(Connection con, String passwd, String email){
        Statement st;
        ResultSet rs;
        String State="select Passwd FROM GymUser WHERE Email = %s";
        boolean PasswdValidate = false;
        try{
            st = con.createStatement();
            rs= st.executeQuery(String.format(State,email));
            while(rs.next()){
                String password = rs.getString(1);
                if(passwd == password){
                    PasswdValidate = true;
                }
                else{
                    PasswdValidate= false;
                }
            }
        }catch(Exception e){
            System.out.println("Napaka na povezavi z podatkovnobazo" + e.getMessage());
        }
        return  PasswdValidate;
    }

    // UsserCheck...... checking if user exists


    static Boolean UsersEmailCheck(Connection con,String email){
        Statement st;
        ResultSet rs;
        String State="select Username FROM GymUser WHERE Email = %s";
        boolean EmailValidate = false;
        try{
            st = con.createStatement();
            rs= st.executeQuery(String.format(State,email));
            if(!rs.next()){
                EmailValidate=true;
            }
            else{
                EmailValidate=false;
            }
        }catch(Exception e){
            System.out.println("Napaka pri preverjanju obstoja uporabnika");
        }
        return  EmailValidate;
    }

// signUp user to mysql database

    static void UsserSignUp(Connection con,int UserId,String Username,String email,String passwd){
        Statement st;
        String State = "INSERT INTO GymUser VALUES (%d,%s,%s,%s)";
        try{
            st=con.createStatement();
            st.executeQuery(String.format(State,UserId,Username,email,passwd));

        }catch(Exception e){
            System.out.println("Cant add User to database"+ e.getMessage());
        }

    }

// Set users Gym location

    static void UsserGymChoice(Connection con,int UserID,int GymId ){
        Statement st;
        String State = "INSERT INTO UserToGymId VALUES (%d,%d)";
        try{
            st=con.createStatement();
            st.executeQuery(String.format(State,UserID,GymId));

        }catch(Exception e){
            System.out.println("Adding user to gym error : " + e.getMessage());
        }

    }

// get gym id for fn  UsserGymChoice

    static Integer GetGymId(Connection con, int UserId){
        Statement st;
        ResultSet rs;
        int gymId=0;
        String State="select GymId FROM UserToGymId WHERE UserID = %d";
        try{
            st=con.createStatement();
            rs=st.executeQuery(String.format(State,UserId));
            gymId=rs.getInt(1);
        }catch(Exception e){
            System.out.println("GymId error : " + e.getMessage());
        }

        return gymId;
    }
// get UserId  for fn  UsserGymChoice

    static Integer GetUserId(Connection con, String email){
        Statement st;
        ResultSet rs;
        int Userid =0;
        String State="select UserID FROM GymUser WHERE Email = %s";
        try{
            st=con.createStatement();
            rs=st.executeQuery(String.format(State,email));
            Userid = rs.getInt(1);

        }catch(Exception e){
            System.out.println("UserID eror : " + e.getMessage());
        }
        return Userid;
    }

// return max users id

    static Integer MaxUserId(Connection con){
        Statement st;
        ResultSet rs;
        int UsserId = 0;
        String State="select max(UserId) FROM GymUser";
        try{
            st= con.createStatement();
            rs=st.executeQuery(State);
            UsserId= rs.getInt(1) +1;

        }catch(Exception e){
            System.out.println("error at Users MaxId fn" + e.getMessage());
        }
        return UsserId;
    }
    static Boolean UserNameCheck(Connection con,String Username){
        Statement st;
        ResultSet rs;
        Boolean UserName = false;
        String State="select UserId FROM GymUser where Username = %s";
        try{
            st= con.createStatement();
            rs=st.executeQuery(String.format(State,Username));
            if(!rs.next()){
                UserName= true;
            }

        }catch(Exception e){
            System.out.println("error at Users MaxId fn" + e.getMessage());
        }
        return UserName;
    }


}
