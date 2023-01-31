package com.br.formulario.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.dao.DaoPortaAberta;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.PortaAberta;
import com.br.formulario.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServicePortaAberta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoPortaAberta daoPortaAberta;


	public ServicePortaAberta() {

	}
	

	@Transactional
	public PortaAberta salvarOuAtualizar(PortaAberta servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoPortaAberta.salveOrUpdate(servico);
	}
	
	
	public PortaAberta porId(Integer id) {
		// TODO Auto-generated method stub
		return daoPortaAberta.consultaPorId(id);
	}
	
	

	public List<PortaAberta> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoPortaAberta.consultarPorFiltrados(filtroDePesquisa);
	}
	


	public List<PortaAberta> consultarTodos() throws ObjetoNaoEncontradoException {
		return daoPortaAberta.recuperarObjetosAtivos();
		
	}


	public List<PortaAberta> consultarPorAno(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
		// TODO Auto-generated method stub
		return daoPortaAberta.consultarPorAno(filtro);
	}
	
	
}
