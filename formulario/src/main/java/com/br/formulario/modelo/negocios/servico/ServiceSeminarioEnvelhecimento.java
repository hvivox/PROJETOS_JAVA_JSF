package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoSeminarioEnvelhecimento;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.SeminarioEnvelhecimento;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceSeminarioEnvelhecimento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoSeminarioEnvelhecimento daoSeminarioEnvelhecimento;
	
	
	public ServiceSeminarioEnvelhecimento() {
		
	}
	

	@Transactional
	public SeminarioEnvelhecimento salvarOuAtualizar(SeminarioEnvelhecimento SeminarioEnvelhecimento) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoSeminarioEnvelhecimento.salveOrUpdate(SeminarioEnvelhecimento);
	}
	
	
	public SeminarioEnvelhecimento porId(Integer id) {
		// TODO Auto-generated method stub
		return daoSeminarioEnvelhecimento.consultaPorId(id);
	}
	
	
	public int totalCadastrados() throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoSeminarioEnvelhecimento.totalRegistros();
	}
	

	public List<SeminarioEnvelhecimento> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoSeminarioEnvelhecimento.consultarPorFiltrados(filtroDePesquisa);
	}
	


	public List<SeminarioEnvelhecimento> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoSeminarioEnvelhecimento.recuperarObjetosAtivos();		
	}
	
	
	public List<SeminarioEnvelhecimento> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoSeminarioEnvelhecimento.consultarPorAno(filtro);
	}
	
	
}
