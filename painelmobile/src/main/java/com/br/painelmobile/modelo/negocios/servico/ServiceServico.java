package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoServico;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Servico;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoServico daoServico;


	public ServiceServico() {

	}
	

	@Transactional
	public Servico salvarOuAtualizar(Servico servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoServico.salveOrUpdate(servico);
	}
	
	
	public Servico porId(Integer id) {
		// TODO Auto-generated method stub
		return daoServico.consultaPorId(id);
	}
	
	

	public List<Servico> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoServico.consultarPorFiltrados(filtroDePesquisa);
	}


	public List<Servico> consultarServicoPorIdCategoria(Integer id) throws ObjetoNaoEncontradoException {
		return daoServico.consultarServicoPorIdCategoria(id);
		
	}
	

	
}
