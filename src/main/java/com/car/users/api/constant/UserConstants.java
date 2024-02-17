package com.car.users.api.constant;

public class UserConstants {
	
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	public static final String PHONE_REGEX = "^\\(?\\d{2}\\)?\\d{5}\\-?\\d{4}$";
	public static final String PASSOWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

	public static final String EMAIL = "email";
	public static final String LOGIN = "login";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String BIRTHDAY = "birthday";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone";

}
