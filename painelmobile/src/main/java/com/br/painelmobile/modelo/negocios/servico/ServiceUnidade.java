package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoUnidade;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Unidade;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceUnidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoUnidade daoUnidade;


	public ServiceUnidade() {

	}
	

	@Transactional
	public Unidade salvarOuAtualizar(Unidade unidade) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoUnidade.salveOrUpdate(unidade);
	}
	
	
	public Unidade porId(Integer id) {
		// TODO Auto-generated method stub
		return daoUnidade.consultaPorId(id);
	}
	
	

	public List<Unidade> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoUnidade.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
}
