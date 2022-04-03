package com.example.mobile30_03;

import java.util.List;

public class User {
    private static List<User> users;
    int id;
    String username;
    String password;


    public User(String username,String password) {
        this.username = username;
        this.password = password;
        id = users.size()+1;
    }

    // returns -1 on error, Users id on success.
    public int addNewUser(String username, String password){
        if(usernameExists(username)){
            System.out.println("Username already exists");
            return -1;
        }else{
            User register = users.add(new User(username,password));
            //TODO
        }

    }

    public static boolean tryToLogin(String username, String password){
    }

    public static boolean usernameExists(String username){
        boolean flag = false;
        for (User user : users) {
            if (user.username.contains(username)) {
                flag = true;
            }
        }
        return flag;
    }

    static List<User> getUserList() {
        return users;
    }
}
