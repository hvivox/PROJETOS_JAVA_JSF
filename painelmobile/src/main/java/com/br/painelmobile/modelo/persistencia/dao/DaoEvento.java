package com.br.painelmobile.modelo.persistencia.dao;

import java.util.Date;
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
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Evento;
import com.br.painelmobile.util.manipularDados.ConversorData;

public class DaoEvento extends DataAccessObject<Evento> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoEvento(EntityManager manager) {
		super(Evento.class, manager);
	}


	/**
	 * UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM
	 * PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException
	 */
	@SuppressWarnings("unchecked")
	public List<Evento> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Evento.class);

		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInitivos() == false) {
			criteria.add(Restrictions.eq("estatus", EstatusEntidadeEnum.ATIVO));
		}

		List<Evento> resultado = criteria.addOrder(Order.asc("titulo")).list();

		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}


	/**
	 * MOSTRA EVENTOS ATIVOS E COM DATA VIGÊNCIA MAIOR OU IGUAL A DATA ATUAL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Evento> consultarEventosAtivosPelaDtaVigencia() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Evento.class);

		// Select * from Evento where dtaEncerramento >= DataAtual AND estatus =
		// 'ATIVO' ORDER BY dtaEncerramento

		criteria.add(Restrictions.ge("dtaEncerramento",
				ConversorData.converteDateParaCalendar(new Date())));

		criteria.add(Restrictions.eq("estatus", EstatusEntidadeEnum.ATIVO));

		List<Evento> resultado = criteria.addOrder(Order.asc("dtaEncerramento")).list();

		return resultado;
	}
	
	
	
	/**
	 * MOSTRA EVENTOS APENAS OS EVENTOS DO DIA CORRENTE
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Evento> exibirEventosDoDia() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Evento.class);

		// Select * from Evento where dtaEncerramento >= DataAtual AND estatus =
		// 'ATIVO' ORDER BY dtaEncerramento

		criteria.add(Restrictions.eq("dtaEncerramento",
				ConversorData.converteDateParaCalendar(new Date())));
		
		criteria.add(Restrictions.eq("estatus", EstatusEntidadeEnum.ATIVO));

		List<Evento> resultado = criteria.addOrder(Order.asc("dtaEncerramento")).list();

		return resultado;
	}
	
	
	
}
