package com.br.painelmobile.modelo.negocios.excecao;

import org.hibernate.PropertyValueException;

public class campoObrigatorioNaoPreenchido extends PropertyValueException{
	private static final long serialVersionUID = -4317710528962040343L;

	public campoObrigatorioNaoPreenchido(String message, String entityName,
			String propertyName) {
		super(message, entityName, propertyName);
		// TODO Auto-generated constructor stub
	}

	
	

	
	
	
}
