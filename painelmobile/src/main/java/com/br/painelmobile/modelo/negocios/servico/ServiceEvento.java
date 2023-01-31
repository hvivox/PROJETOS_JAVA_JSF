package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoEvento;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceEvento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoEvento daoEvento;


	public ServiceEvento() {

	}
	

	@Transactional
	public Evento salvarOuAtualizar(Evento evento) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoEvento.salveOrUpdate(evento);
	}
	
	
	public Evento porId(Integer id) {
		// TODO Auto-generated method stub
		return daoEvento.consultaPorId(id);
	}
	
	

	public List<Evento> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoEvento.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	
	public List<Evento> consultarEventosAtivoPorVigencia() {
		return daoEvento.consultarEventosAtivosPelaDtaVigencia();
	}
	
	
	public List<Evento> exibirEventoDoDia() {
		return daoEvento.exibirEventosDoDia();
	}
	
	
}
