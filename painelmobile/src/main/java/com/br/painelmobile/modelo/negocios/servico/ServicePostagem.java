package com.br.painelmobile.modelo.negocios.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.dao.DaoPostagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Postagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.PostagemComCategoria;
import com.br.painelmobile.util.cdi.qualifier.Transactional;


//CLASSE UTILIZADA PARA AGRUPAR OS METODOS DE ACESSO AOS DADOS E UTILIZAR O DAO GENERICO
public class ServicePostagem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DaoPostagem daoPostagem;
	
	public ServicePostagem() {
		
	}
	
	
	@Transactional
	public Postagem salvarOuAtualizar(Postagem Postagem) throws campoObrigatorioNaoPreenchido, ObjetoNaoEncontradoException, ObjetoJaExistenteException, ObjetoTransienteSendoPersistido{
		return daoPostagem.salveOrUpdate(Postagem);	
	}
	
	
	
	public List<Postagem> buscarPostagemPorQuantidade(int qtdLinhasExbir) throws WSTratamentoExcecaoGeral {
		return daoPostagem.buscarPostagemPorQuantidade(qtdLinhasExbir);		
	}

	
	public List<PostagemComCategoria> buscarPostagemComCategoriaPorQuantidade(int qtdLinhasExbir) throws WSTratamentoExcecaoGeral {
		return daoPostagem.buscarPostagemComCategoriaPorQuantidade(qtdLinhasExbir);		
	}
	
	
	public Postagem recuperarPorId(Integer id) throws ObjetoNaoEncontradoException, EntityIdNuloException{
		return daoPostagem.recuperarObjeto(id);
	}
	
	
}
