package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoDifusaoDanca;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DifusaoDanca;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceDifusaoDanca implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoDifusaoDanca daoDifusaoDanca;
	
	
	public ServiceDifusaoDanca() {
		
	}
	

	@Transactional
	public DifusaoDanca salvarOuAtualizar(DifusaoDanca servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoDifusaoDanca.salveOrUpdate(servico);
	}
	
	
	public DifusaoDanca porId(Integer id) {
		// TODO Auto-generated method stub
		return daoDifusaoDanca.consultaPorId(id);
	}
	
	

	public List<DifusaoDanca> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoDifusaoDanca.consultarPorFiltrados(filtroDePesquisa);
	}
	


	public List<DifusaoDanca> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoDifusaoDanca.recuperarObjetosAtivos();		
	}
	
	
	public List<DifusaoDanca> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoDifusaoDanca.consultarPorAno(filtro);
	}
	
	
}
