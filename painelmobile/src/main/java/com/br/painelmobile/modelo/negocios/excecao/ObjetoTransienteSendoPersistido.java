package com.br.painelmobile.modelo.negocios.excecao;

public class ObjetoTransienteSendoPersistido extends Exception {

	private static final long serialVersionUID = 2308572969524136229L;
	private static final String msg = "É necessário preencher todos os campo obrigatórios";
	
	
	public ObjetoTransienteSendoPersistido(){
		super(msg);
	}
	
}
