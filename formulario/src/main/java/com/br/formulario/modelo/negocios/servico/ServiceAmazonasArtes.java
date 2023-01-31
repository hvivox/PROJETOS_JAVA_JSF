package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoAmazonasArtes;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceAmazonasArtes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoAmazonasArtes daoAmazonasArtes;


	public ServiceAmazonasArtes() {

	}
	

	@Transactional
	public AmazonasArtes salvarOuAtualizar(AmazonasArtes servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoAmazonasArtes.salveOrUpdate(servico);
	}
	
	
	public AmazonasArtes porId(Integer id) {
		// TODO Auto-generated method stub
		return daoAmazonasArtes.consultaPorId(id);
	}
	
	

	public List<AmazonasArtes> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoAmazonasArtes.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public Integer consultarTotInscritosPorModalidade(Integer id) throws ObjetoNaoEncontradoException {
		return daoAmazonasArtes.consultarTotInscritosPorModalidade(id);
		
	}


	public List<AmazonasArtes> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoAmazonasArtes.recuperarObjetosAtivos();
		
	}
	
	
}
