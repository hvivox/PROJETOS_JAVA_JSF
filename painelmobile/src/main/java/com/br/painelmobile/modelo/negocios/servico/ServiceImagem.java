package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoImagem;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;
import com.br.painelmobile.util.cdi.qualifier.Transactional;

//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServiceImagem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoImagem daoImagem;


	public ServiceImagem() {

	}
	

	@Transactional
	public Imagem salvarOuAtualizar(Imagem imagem) throws campoObrigatorioNaoPreenchido,
			ObjetoNaoEncontradoException, ObjetoJaExistenteException,
			ObjetoTransienteSendoPersistido {
		return daoImagem.salveOrUpdate(imagem);
	}
	
	
	public Imagem porId(Integer id) {
		// TODO Auto-generated method stub
		return daoImagem.consultaPorId(id);
	}
	
	

	public List<Imagem> consultarPorFiltro(FiltroPesquisaPadrao filtroDePesquisa)
			throws ObjetoNaoEncontradoException {
		return daoImagem.consultarPorFiltrados(filtroDePesquisa);
	}
	
	@Transactional
	public void excluirPorId(Integer id) throws ObjetoNaoEncontradoException, EntityIdNuloException{
		daoImagem.removerObjetoPorId(id);	
	}


	public List<Imagem> consultarImagensPorGaleria(Integer id) throws ObjetoNaoEncontradoException {		
		return daoImagem.consultarImagensPorGaleria(id);
	}
	
	
	
}
