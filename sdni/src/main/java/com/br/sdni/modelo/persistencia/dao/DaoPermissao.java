package com.br.sdni.modelo.persistencia.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;

public class DaoPermissao extends DataAccessObject<Permissao> {
	
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	public DaoPermissao(EntityManager manager) {
		super(Permissao.class, manager);
	}
	
	
}
