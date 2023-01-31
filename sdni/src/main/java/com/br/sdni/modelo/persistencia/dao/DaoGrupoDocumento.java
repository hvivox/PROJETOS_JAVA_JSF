package com.br.sdni.modelo.persistencia.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.br.sdni.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Documento;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.GrupoDocumento;

public class DaoGrupoDocumento extends DataAccessObject<GrupoDocumento> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoGrupoDocumento(EntityManager manager) {
		super(GrupoDocumento.class, manager);
	}


	@SuppressWarnings("deprecation")
	private Criteria criarCriteriaParaFiltro(FiltroPesquisaPadrao filtro) {

		Session session = (Session) getManager();
		Criteria criteria = session.createCriteria(GrupoDocumento.class);

		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("nomeGrupoDoc", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInativos() == false) {
			criteria.add(Restrictions.eq("status", StatusEntidadeEnum.ATIVO));
		}

		return criteria;
	}



	@SuppressWarnings("unchecked")
	public List<GrupoDocumento> consultarPorFiltrados(FiltroPesquisaPadrao filtro) 
			 {

		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		}
		else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		
		//ORDENA EM ORDEM ALFABÉTICA NA PRIMEIRA CHAMADA DA TELA
		else if (StringUtils.isBlank( filtro.getPropriedadeOrdenacao() ) ) {
			criteria.addOrder(Order.asc("nomeGrupoDoc"));
		}
		
		
		List<GrupoDocumento> resultado = criteria.list();
		
	
		return resultado;
	}
	
	
	public List<GrupoDocumento> consultarGruposAtivosOrdemAlfabetica(){
		String query = "from GrupoDocumento gd where gd.status = :status order by gd.nomeGrupoDoc";
		
		Query q = getManager().createQuery(query);
		q.setParameter("status", StatusEntidadeEnum.ATIVO);
		
		return (List<GrupoDocumento>) q.getResultList();		
	}
	
	
	/**
	 * METODO RETORNA A QUANTIDADE DE TOTAL DE OBJETOS CONSIDERANDO TAMBÉM O FILTRO DOS CAMPOS DE PESQUISA
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException
	 */
	public int quantidadeFiltrados(FiltroPesquisaPadrao filtro) {
		
		//NAO PODE DEIXAR ESSA CHAMADA DE FORA
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
			
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	
}
