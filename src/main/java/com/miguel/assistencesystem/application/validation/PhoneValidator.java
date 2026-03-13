package com.miguel.assistencesystem.application.validation;

public final class PhoneValidator {

	private PhoneValidator() {}
	
	public static boolean isValid(String phone) {
		 
        if (phone == null || phone.isBlank())
            return false;

        String digits = phone.replaceAll("\\D", "");

        return digits.length() >= 10 && digits.length() <= 13;	    
	}
}