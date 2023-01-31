package com.br.painelmobile.controle.webserver.excecoes;

import java.io.Serializable;

/**
 * @author hvivox
 *Classe utilizada para representar todas as excecoes referente ao webService
 */
public class WSTratamentoExcecaoGeral extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public WSTratamentoExcecaoGeral() {
		super();
	}

	public WSTratamentoExcecaoGeral(String msg) {
		super(msg);
	}

	public WSTratamentoExcecaoGeral(String msg, Exception e) {
			super(msg, e);
	}
}
