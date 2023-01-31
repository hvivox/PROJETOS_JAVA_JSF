package com.br.painelmobile.modelo.persistencia.dao;


import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Mensagem;



public class DaoMensagem extends	DataAccessObject<Mensagem> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	public DaoMensagem(EntityManager manager) {
		super(Mensagem.class, manager);
	}
	
}
