package com.br.painelmobile.modelo.persistencia.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.LogFactory;

import com.br.painelmobile.controle.webserver.excecoes.WSTratamentoExcecaoGeral;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Postagem;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.PostagemComCategoria;

public class DaoPostagem extends DataAccessObject<Postagem> {

	private static final long serialVersionUID = 1L;


	@Inject
	// Instancia o EntityManager antes do DaoPostagem
	public DaoPostagem(EntityManager manager) {
		super(Postagem.class, manager);
	}


	/*
	 * SELECT DISTINCT wp_posts.* FROM wp_posts LEFT JOIN wp_term_relationships
	 * ON (wp_posts.ID = wp_term_relationships.object_id) LEFT JOIN
	 * wp_term_taxonomy ON (wp_term_relationships.term_taxonomy_id =
	 * wp_term_taxonomy.term_taxonomy_id) WHERE wp_posts.post_status = 'publish'
	 * AND wp_posts.post_type = 'post' AND wp_term_taxonomy.taxonomy =
	 * 'category' AND wp_term_taxonomy.term_id = 7 ORDER BY post_date DESC limit
	 * 6;
	 */
	/**
	 * @param qtdLinhasExbir numero de linhas a ser listada
	 * @return list de Postagem
	 * @throws WSTratamentoExcecaoGeral verifica se a consulta retorna vazia
	 */
	@SuppressWarnings("unchecked")
	public List<Postagem> buscarPostagemPorQuantidade(int qtdLinhasExbir)
			throws WSTratamentoExcecaoGeral {

		List<Postagem> listaPaginacao = null;
		Query query = getManager().createNativeQuery("SELECT DISTINCT wp_posts.*\r\n"
				+ "FROM wp_posts\r\n"
				+ "LEFT JOIN wp_term_relationships ON (wp_posts.ID = wp_term_relationships.object_id)\r\n"
				+ "LEFT JOIN wp_term_taxonomy ON (wp_term_relationships.term_taxonomy_id = wp_term_taxonomy.term_taxonomy_id)\r\n"
				+ "WHERE wp_posts.post_status = 'publish'\r\n" 
				+ "AND wp_posts.post_type = 'post'\r\n"
				+ "AND wp_term_taxonomy.taxonomy = 'category'\r\n"
				+ "AND wp_term_taxonomy.term_id = 42\r\n" + "ORDER BY post_date DESC limit 6;",
				Postagem.class);

		listaPaginacao = query.getResultList();
			
		
		if (listaPaginacao == null || listaPaginacao.size() == 0
				|| !(listaPaginacao instanceof Object)) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn("Não foi encontradas mensagens.....");
			throw new WSTratamentoExcecaoGeral(
					"{\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

		return listaPaginacao;
	}
	
	
	
	/**@method utilizado para mostrar postagens com categoria e 
	 * @param qtdLinhasExbir numero de linhas a ser listada
	 * @return list de Postagem
	 * @throws WSTratamentoExcecaoGeral verifica se a consulta retorna vazia
	 */
	@SuppressWarnings("unchecked")
	public List<PostagemComCategoria> buscarPostagemComCategoriaPorQuantidade (int qtdLinhasExbir)
			throws WSTratamentoExcecaoGeral {

		List<PostagemComCategoria> listaPaginacao = null;
		Query query = getManager().createNamedQuery("postagemComCategoria");

		listaPaginacao = query.getResultList();
			
		
		if (listaPaginacao == null || listaPaginacao.size() == 0
				|| !(listaPaginacao instanceof Object)) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn("Não foi encontradas mensagens.....");
			throw new WSTratamentoExcecaoGeral(
					"{\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}");
		}

		return listaPaginacao;
	}

	
	


	
	
	/**
	 * @param qtdLinhasExbir numero de linhas a ser listada
	 * @return list de Postagem
	 * @throws WSTratamentoExcecaoGeral verifica se a consulta retorna vazia
	 */
	/*
	 * @SuppressWarnings("unchecked") public List<Postagem>
	 * buscarPostagemPorQuantidade(int qtdLinhasExbir) throws
	 * WSTratamentoExcecaoGeral {
	 * 
	 * List<Postagem> listaPaginacao = null; Query query =
	 * getManager().createQuery("from Postagem " +
	 * "where post_type =:post_type and " +
	 * "post_status =:post_status ORDER BY post_date DESC");
	 * 
	 * query.setParameter("post_type", "post");
	 * query.setParameter("post_status", "publish");
	 * query.setMaxResults(qtdLinhasExbir);
	 * 
	 * listaPaginacao = query.getResultList();
	 * 
	 * if (listaPaginacao == null || listaPaginacao.size() == 0 ||
	 * !(listaPaginacao instanceof Object)) {
	 * LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).
	 * warn("Não foi encontradas mensagens....."); throw new
	 * WSTratamentoExcecaoGeral(
	 * "{\"erro\":\"No momento não é possível exibir as notícias. Por favor tente novamente mais tarde\"}"
	 * ); }
	 * 
	 * return listaPaginacao; }
	 */

}
