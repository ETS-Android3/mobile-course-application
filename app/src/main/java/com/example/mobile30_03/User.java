package com.example.mobile30_03;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class User {
    private static int nextId = 1;
    String username;
    String password;
    String email;
    String phoneNumber;
    int id;

    public User(String username,String password,String email,String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;

        id = nextId++;
    }

}

class AppUsers{
    private static List<User> users = new ArrayList<User>();
    
    public static User getUserById(int id){
        Iterator<User> iter = users.iterator();
        while ( iter.hasNext() ){
            User current = iter.next();
            if (current.id== id){
                return current;
            }
        }
        return new User(null, null, null, null);
    }

    static void populateUsers(){
        users.add(new User("","","furkanaxcakaya2@gmail.com","5524969"));
        users.add(new User("furkan","1231","furkanaxcakaya@gmail.com","554969"));
        users.add(new User("ahmet","2332","1232@gmail.com","3223"));
        users.add(new User("mehmet","mehme","1111@gmail.com","1221"));
        users.add(new User("faruk","faruka","2222@gmail.com","4554"));
        users.add(new User("admin","admin","furkanx54x@gmail.com","5435"));
        users.add(new User("aslihant","421100","trhnaslhnt@gmail.com","5076538444"));
    }



    // returns -1 on error, Users id on success.
    public static int addNewUser(String username, String password, String email, String phone){
        if(usernameExists(username)){
            System.out.println("Username already exists");
            return -1;
        }else{
            User register = new User(username,password,email,phone);
            users.add(register);
            return register.id;
        }
    }

    //returns id on success, -1 on error
    public static int tryToLogin(String username, String password){
        Iterator<User> iter = users.iterator();
        while ( iter.hasNext() ){
            User current = iter.next();
            if (current.username.contentEquals(username) && current.password.contentEquals(password)){
                return current.id;
            }
        }
        return -1;
    }

    public static boolean usernameExists(String username){
        boolean flag = false;
        for (User user : users) if (user.username.contains(username)) flag = true;
        return flag;
    }

    static List<User> getUserList() {
        return users;
    }

}