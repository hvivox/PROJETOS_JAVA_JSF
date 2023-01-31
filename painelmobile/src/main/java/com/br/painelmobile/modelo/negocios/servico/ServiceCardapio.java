package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoCardapio;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceCardapio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoCardapio daoCardapio;


	public ServiceCardapio() {

	}
	

	@Transactional
	public Cardapio salvarOuAtualizar(Cardapio cardapio) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoCardapio.salveOrUpdate(cardapio);
	}
	
	
	public Cardapio porId(Integer id) {
		// TODO Auto-generated method stub
		return daoCardapio.consultaPorId(id);
	}
	
	

	public List<Cardapio> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoCardapio.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public Cardapio consultaUltimoRegistroAtivo() {
		// TODO Auto-generated method stub
		return daoCardapio.consultaUltimoRegistroAtivo();
	}
	
	
	
}
