package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoPromocaoIdoso;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PromocaoIdoso;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServicePromocaoIdoso implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoPromocaoIdoso daoPromocaoIdoso;
	
	
	public ServicePromocaoIdoso() {
		
	}
	

	@Transactional
	public PromocaoIdoso salvarOuAtualizar(PromocaoIdoso promocaoIdoso) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoPromocaoIdoso.salveOrUpdate(promocaoIdoso);
	}
	
	
	public PromocaoIdoso porId(Integer id) {
		// TODO Auto-generated method stub
		return daoPromocaoIdoso.consultaPorId(id);
	}
	
	

	public List<PromocaoIdoso> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoPromocaoIdoso.consultarPorFiltrados(filtroDePesquisa);
	}
	


	public List<PromocaoIdoso> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoPromocaoIdoso.recuperarObjetosAtivos();		
	}
	
	
	public List<PromocaoIdoso> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoPromocaoIdoso.consultarPorAno(filtro);
	}
	
	
}
