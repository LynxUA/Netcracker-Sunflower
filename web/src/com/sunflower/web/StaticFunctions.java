package com.sunflower.web;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andriy on 11/20/2014.
 */
public class StaticFunctions {
    private static final Pattern EMAIL_PATTERN = Pattern.compile( "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");

    public static boolean isValidPassword(String password){
//        Matcher matcher = PASSWORD_PATTERN.matcher(password);
//        return matcher.matches();
        return true;
    }

    public static boolean isValidEmail(String login){
        Matcher matcher = EMAIL_PATTERN.matcher(login);
        return matcher.matches();
    }

    public static boolean isEmailExist(String login) {
        return 0 == login.compareTo("andry-124@mail.ru");
    }

    public static String getHashCode(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Add password bytes to digest
        md.update(password.getBytes());
        //Get the hash's bytes
        byte[] hash = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< hash.length ;i++)
        {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        return sb.toString();
    }

}
