package com.br.sdni.modelo.persistencia.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;

public class DaoGrupo extends DataAccessObject<Grupo> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoGrupo(EntityManager manager) {
		super(Grupo.class, manager);
	}
	

	
	
}
