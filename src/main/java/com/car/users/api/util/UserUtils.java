package com.car.users.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.car.users.api.constant.UserConstants;

public class UserUtils {
	
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(UserConstants.EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(UserConstants.PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    public static boolean isValidLength(String input, int maxLength) {
        return input.length() <= maxLength;
    }
    
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(UserConstants.PASSOWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
	
}
