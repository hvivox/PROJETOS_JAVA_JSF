package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoGaleriaImagem;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.GaleriaImagem;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceGaleriaImagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoGaleriaImagem daoGaleriaImagem;


	public ServiceGaleriaImagem() {

	}
	

	@Transactional
	public GaleriaImagem salvarOuAtualizar(GaleriaImagem galeriaImagem) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoGaleriaImagem.salveOrUpdate(galeriaImagem);
	}
	
	
	public GaleriaImagem porId(Integer id) {
		// TODO Auto-generated method stub
		return daoGaleriaImagem.consultaPorId(id);
	}
	
	

	public List<GaleriaImagem> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoGaleriaImagem.consultarPorFiltrados(filtroDePesquisa);
	}	

	


	public GaleriaImagem consultarPorObjeto(GaleriaImagem galeria) throws ObjetoNaoEncontradoException, EntityIdNuloException{
		return daoGaleriaImagem.consultaObjeto(galeria);
	}

	
	
}
