package com.car.users.api.util;

import java.util.Calendar;
import java.util.regex.Pattern;

import com.car.users.api.constant.CarConstants;

public class CarUtils {
	
	public static boolean isValidYear(int ano) {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        return ano >= 1900 && ano <= anoAtual;
    }
	
    public static boolean isValidLicensePlate(String placa) {
        return Pattern.matches(CarConstants.LICENSE_PLATE_REGEX, placa);
    }
	
}
