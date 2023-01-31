package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoCicloFaixa;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.CicloFaixa;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceCicloFaixa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoCicloFaixa daoCicloFaixa;
	
	
	public ServiceCicloFaixa() {
		
	}
	

	@Transactional
	public CicloFaixa salvarOuAtualizar(CicloFaixa servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoCicloFaixa.salveOrUpdate(servico);
	}
	
	
	public CicloFaixa porId(Integer id) {
		// TODO Auto-generated method stub
		return daoCicloFaixa.consultaPorId(id);
	}
	
	

	public List<CicloFaixa> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoCicloFaixa.consultarPorFiltrados(filtroDePesquisa);
	}
	


	public List<CicloFaixa> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoCicloFaixa.recuperarObjetosAtivos();		
	}
	
	
	public List<CicloFaixa> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoCicloFaixa.consultarPorAno(filtro);
	}
	
	
}
