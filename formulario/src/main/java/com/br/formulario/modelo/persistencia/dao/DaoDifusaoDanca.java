package com.br.formulario.modelo.persistencia.dao;


import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
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
import com.br.formulario.modelo.persistencia.entidade.mapeadas.DifusaoDanca;


public class DaoDifusaoDanca extends DataAccessObject<DifusaoDanca> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	public DaoDifusaoDanca(EntityManager manager) {
		super(DifusaoDanca.class, manager);
	}


	/** UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	@SuppressWarnings("unchecked")
	public List<DifusaoDanca> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(DifusaoDanca.class);
		
		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("nome", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInitivos() == false) {
			criteria.add(Restrictions.eq("status", StatusEntidadeEnum.ATIVO));
		}		
		
		List<DifusaoDanca> resultado = criteria.addOrder(Order.asc("nome")).list();

		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}
	
	
	
	/** METODO UTULIZADO SOMENTE PELA PAINEL DE RELATORIO - FAZ A PESQUISA PELO FILTRO DO ANO
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	@SuppressWarnings("unchecked")
	public List<DifusaoDanca> consultarPorAno(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		
		List<DifusaoDanca> resultado = (List<DifusaoDanca>) getManager()
				.createQuery("Select difusaoDanca From DifusaoDanca difusaoDanca where year(difusaoDanca.dtaInscricao) = :ano")
				.setParameter("ano", Integer.parseInt(filtro.getAno()) ).getResultList();
		
		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		
		return resultado;
		
	}



}
