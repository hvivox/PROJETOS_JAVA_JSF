package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoParceiro;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Parceiro;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceParceiro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoParceiro daoParceiro;


	public ServiceParceiro() {

	}
	

	@Transactional
	public Parceiro salvarOuAtualizar(Parceiro servico) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoParceiro.salveOrUpdate(servico);
	}
	
	
	public Parceiro porId(Integer id) {
		// TODO Auto-generated method stub
		return daoParceiro.consultaPorId(id);
	}
	
	

	public List<Parceiro> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoParceiro.consultarPorFiltrados(filtroDePesquisa);
	}
	
	
	public List<Parceiro> consultarParceiroPorIdCategoria(Integer id) throws ObjetoNaoEncontradoException {
		return daoParceiro.consultarParceiroPorIdCategoria(id);
		
	}
	
	
}
