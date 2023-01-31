package com.br.painelmobile.modelo.persistencia.dao;


import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Cardapio;



public class DaoCardapio extends	DataAccessObject<Cardapio> {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	public DaoCardapio(EntityManager manager) {
		super(Cardapio.class, manager);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Cardapio> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = getManager().unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cardapio.class);

		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInitivos() == false) {
			criteria.add(Restrictions.eq("estatus", EstatusEntidadeEnum.ATIVO));
		}

		List<Cardapio> resultado = criteria.addOrder(Order.asc("titulo")).list();

		if (resultado.size() == 0) {
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}	
	
	
	/**
	 * OBTER O ULTIMO REGISTRO ATIVO
	 * @return
	 */
	public Cardapio consultaUltimoRegistroAtivo(){		
		Cardapio cadapio = new Cardapio();
		try {
			
			String query = "from Cardapio c where "
					+ " c.id = (select max(d.id) from Cardapio d where d.estatus = :estatus)";
			Query q = getManager().createQuery(query);
			q.setParameter("estatus", EstatusEntidadeEnum.ATIVO	);			
			
			return (Cardapio) q.getSingleResult();
			
		}catch(NoResultException e) {
			//caso o resultado seja vazio será enviado um objeto vazio
			return cadapio;
		}	
		
	}	
	
}
