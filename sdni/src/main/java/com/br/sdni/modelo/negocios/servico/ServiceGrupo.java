package com.br.sdni.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.sdni.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.sdni.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.sdni.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.sdni.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.sdni.modelo.persistencia.dao.DaoGrupo;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Grupo;
import com.br.sdni.util.cdi.qualifier.Transactional;


public class ServiceGrupo implements Serializable {
	//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
	private static final long serialVersionUID = 1L;

	@Inject
	private DaoGrupo daoGrupo;

	public ServiceGrupo() {

	}
	
	
	@Transactional
	public Grupo salvarOuAtualizar(Grupo grupo)
			throws campoObrigatorioNaoPreenchido, ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, ObjetoTransienteSendoPersistido {

		return daoGrupo.salveOrUpdate(grupo);
	}
	
	
	
	public List<Grupo> todos() {
		return daoGrupo.consultarTodos();
		
		/*return this.manager.createQuery("from Grupo", Grupo.class)
				.getResultList();*/
	}
	
	
	
	public Grupo porId(Integer id) {
		return daoGrupo.consultaPorId(id);
		/*return this.manager.find(Grupo.class, id);*/
	}
	
	
	
}
