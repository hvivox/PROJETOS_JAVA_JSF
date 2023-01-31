package com.br.sdni.util.security;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public abstract class GenerateHashPasswordUtil {
	private static Object salt;
	
	public static String generateHash(String password) {
		MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMensageDisterPassword();
		String encodePassword = digestPasswordEncoder.encodePassword(password, salt);
		return encodePassword;
		
	}
	
	
	private static MessageDigestPasswordEncoder getInstanceMensageDisterPassword() {
		//informo o tipo de encode desejado
		MessageDigestPasswordEncoder digestPasswordEncoder = new MessageDigestPasswordEncoder("MD5");
		return digestPasswordEncoder;
	}
	
	
	// metodo que faz a validação como não usamos salt deixei null
	public static boolean isPasswordValid(String password, String hashPassword) {
		MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMensageDisterPassword();
		
		
		return digestPasswordEncoder.isPasswordValid(hashPassword, password, salt);
	}
	
	
}
