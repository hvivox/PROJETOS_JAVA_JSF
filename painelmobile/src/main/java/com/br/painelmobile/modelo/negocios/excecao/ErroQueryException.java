package com.br.painelmobile.modelo.negocios.excecao;

public class ErroQueryException extends Exception {

	private static final long serialVersionUID = -2597810983486579694L;
	private static final String msg = "Problemas na consulta, contate o administrador do sistema";
	public ErroQueryException(){
		super(msg);
	}
	
	
}
