package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoSelecao;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Selecao;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceSelecao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoSelecao daoSelecao;


	public ServiceSelecao() {

	}
	

	@Transactional
	public Selecao salvarOuAtualizar(Selecao selecao) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoSelecao.salveOrUpdate(selecao);
	}
	
	
	public Selecao porId(Integer id) {
		// TODO Auto-generated method stub
		return daoSelecao.consultaPorId(id);
	}
	
	

	public List<Selecao> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoSelecao.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	
	public List<Selecao> consultarEventosAtivoPorVigencia() {
		return daoSelecao.consultarSelecaoAtivosPelaDtaVigencia();
	}
	
	
	
}
