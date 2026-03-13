package com.miguel.assistencesystem.application.validation;

public final class CpfValidator {

	private CpfValidator() {}

	public static boolean isValid(String cpf) {
		
		if (cpf == null || cpf.isBlank())
            return false;
		
		cpf = cpf.replaceAll("\\D", "");
		
		if (cpf.length() != 11)
            return false;
	
	
		if (cpf.matches("(\\d)\\1{10}"))
            return false;
		
		try {
            int sum = 0;
            for (int i = 0; i < 9; i++)
                sum += (cpf.charAt(i) - '0') * (10 - i);

            int digit1 = (sum * 10) % 11;
            if (digit1 == 10) digit1 = 0;

            if (digit1 != (cpf.charAt(9) - '0'))
                return false;

            sum = 0;
            for (int i = 0; i < 10; i++)
                sum += (cpf.charAt(i) - '0') * (11 - i);

            int digit2 = (sum * 10) % 11;
            if (digit2 == 10) digit2 = 0;

            return digit2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }	
}
