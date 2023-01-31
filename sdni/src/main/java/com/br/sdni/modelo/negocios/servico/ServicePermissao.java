package com.br.sdni.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.sdni.modelo.persistencia.dao.DaoPermissao;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Permissao;


public class ServicePermissao implements Serializable {
	//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
	private static final long serialVersionUID = 1L;

	@Inject
	private DaoPermissao daoPermissao;

	public ServicePermissao() {

	}	
	
	public List<Permissao> todos() {
		return daoPermissao.consultarTodos();
		
		/*return this.manager.createQuery("from Grupo", Grupo.class)
				.getResultList();*/
	}
	
	
	
	public Permissao porId(Integer id) {
		return daoPermissao.consultaPorId(id);
		/*return this.manager.find(Grupo.class, id);*/
	}
	
	
	
}
