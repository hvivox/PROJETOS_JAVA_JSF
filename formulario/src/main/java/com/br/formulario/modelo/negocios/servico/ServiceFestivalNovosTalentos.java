package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoFestivalNovosTalentos;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.FestivalNovosTalentos;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceFestivalNovosTalentos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoFestivalNovosTalentos daoFestivalNovosTalentos;


	public ServiceFestivalNovosTalentos() {

	}
	

	@Transactional
	public FestivalNovosTalentos salvarOuAtualizar(FestivalNovosTalentos servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoFestivalNovosTalentos.salveOrUpdate(servico);
	}
	
	
	public FestivalNovosTalentos porId(Integer id) {
		// TODO Auto-generated method stub
		return daoFestivalNovosTalentos.consultaPorId(id);
	}
	
	

	public List<FestivalNovosTalentos> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoFestivalNovosTalentos.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public Integer consultarTotInscritosPorModalidade(Integer id) throws ObjetoNaoEncontradoException {
		return daoFestivalNovosTalentos.consultarTotInscritosPorModalidade();
		
	}


	public List<FestivalNovosTalentos> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoFestivalNovosTalentos.recuperarObjetosAtivos();
		
	}


	public List<FestivalNovosTalentos> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoFestivalNovosTalentos.consultarPorAno(filtro);
	}
	
	
}
