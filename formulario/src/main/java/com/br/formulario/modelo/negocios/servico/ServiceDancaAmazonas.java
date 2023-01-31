package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoDancaAmazonas;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DancaAmazonas;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceDancaAmazonas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoDancaAmazonas daoDancaAmazonas;


	public ServiceDancaAmazonas() {

	}
	

	@Transactional
	public DancaAmazonas salvarOuAtualizar(DancaAmazonas servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoDancaAmazonas.salveOrUpdate(servico);
	}
	
	
	public DancaAmazonas porId(Integer id) {
		// TODO Auto-generated method stub
		return daoDancaAmazonas.consultaPorId(id);
	}
	
	

	public List<DancaAmazonas> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoDancaAmazonas.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public Integer consultarTotInscritosPorModalidade(Integer id) throws ObjetoNaoEncontradoException {
		return daoDancaAmazonas.consultarTotInscritosPorModalidade(id);
		
	}
	
	
}
