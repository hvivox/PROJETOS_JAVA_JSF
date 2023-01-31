package com.br.painelmobile.modelo.persistencia.dao;


import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.CategoriaParceiro;



public class DaoCategoriaParceiro extends	DataAccessObject<CategoriaParceiro> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	public DaoCategoriaParceiro(EntityManager manager) {
		super(CategoriaParceiro.class, manager);
	}
	
	
	/**
	 * UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException
	 */
	@SuppressWarnings("unchecked")
	public List<CategoriaParceiro> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(CategoriaParceiro.class);
		
		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("nomeCategoria", filtro.getTitulo(), MatchMode.ANYWHERE));
		}
		
		List<CategoriaParceiro> resultado = criteria.addOrder(Order.asc("nomeCategoria")).list();
		
		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn("Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}
	
	
	
}
