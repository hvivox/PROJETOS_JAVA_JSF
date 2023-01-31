package com.br.formulario.modelo.persistencia.dao;


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

import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.AmazonasArtes;


public class DaoAmazonasArtes extends DataAccessObject<AmazonasArtes> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	public DaoAmazonasArtes(EntityManager manager) {
		super(AmazonasArtes.class, manager);
	}


	/** UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	@SuppressWarnings("unchecked")
	public List<AmazonasArtes> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(AmazonasArtes.class);
		
		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInitivos() == false) {
			criteria.add(Restrictions.eq("status", StatusEntidadeEnum.ATIVO));
		}		
		
		List<AmazonasArtes> resultado = criteria.addOrder(Order.asc("nomeGrupo")).list();

		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}
	

	
	public Integer consultarTotInscritosPorModalidade(Integer idModalidade)
			throws ObjetoNaoEncontradoException {
		Integer resultado = Integer.valueOf(getManager()
				.createQuery(
						"select count(amArtes) from AmazonasArtes amArtes where amArtes.modAmArtes.id = ?")
				.setParameter(1, idModalidade).getSingleResult().toString());

		if (!(resultado instanceof Object) || resultado==0) {
			resultado=0;
			
		}
		return resultado;

	}

}
