package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoCategoriaParceiro;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceCategoriaParceiro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoCategoriaParceiro daoCategoriaParceiro;


	public ServiceCategoriaParceiro() {

	}
	

	@Transactional
	public CategoriaParceiro salvarOuAtualizar(CategoriaParceiro categoriaParceiro) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoCategoriaParceiro.salveOrUpdate(categoriaParceiro);
	}
	
	
	public CategoriaParceiro porId(Integer id) {
		// TODO Auto-generated method stub
		return daoCategoriaParceiro.consultaPorId(id);
	}
	
	

	public List<CategoriaParceiro> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoCategoriaParceiro.consultarPorFiltrados(filtroDePesquisa);
	}
	

	
}
