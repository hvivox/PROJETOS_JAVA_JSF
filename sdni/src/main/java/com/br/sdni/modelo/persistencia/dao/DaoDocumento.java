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

public class DaoDocumento extends DataAccessObject<Documento> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoDocumento(EntityManager manager) {
		super(Documento.class, manager);
	}


	/* BUSCA
	 * FILTRADA------------------------------------------------------------------*/

	@SuppressWarnings("deprecation")
	private Criteria criarCriteriaParaFiltro(FiltroPesquisaPadrao filtro) {

		Session session = (Session) getManager();
		Criteria criteria = session.createCriteria(Documento.class)

				// IMPORTANTE: utilizado para ordenação com join o sortby da tela só
				// encontra se explicitar o alias conforme o nome da class
				.createAlias("grupoDoc", "grupoDoc");

		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			// PODE SE NECESSÁRIO TROCA O NOME DO CAMPO DE PESQUIA "TITULO"
			criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInativos() == false) {
			criteria.add(Restrictions.eq("status", StatusEntidadeEnum.ATIVO));
		}

		return criteria;
	}


	@SuppressWarnings("unchecked")
	public List<Documento> consultarPorFiltrados(FiltroPesquisaPadrao filtro) {

		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		}
		else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		List<Documento> resultado = criteria.list();

		return resultado;
	}


	/** METODO RETORNA A QUANTIDADE DE TOTAL DE OBJETOS CONSIDERANDO TAMBÉM O FILTRO DOS CAMPOS DE
	 * PESQUISA
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	public int quantidadeFiltrados(FiltroPesquisaPadrao filtro) {

		// NAO PODE DEIXAR ESSA CHAMADA DE FORA
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	/* FIM - BUSCA
	 * FILTRADA-------------------------------------------------------------------------------------
	 * ----- */


	/** Utilizado para pegar resultado de duas entidades, por exemplo Documento e GrupoDocumento
	 * para preenher o combobox
	 * @param id da entidade
	 * @return o join entre a entetidade principal e a secundária */
	public Documento buscarPorIdDocumentoEGrupo(Integer id) {

		Documento resultado = (Documento) getManager().createQuery(
				"Select doc From Documento doc INNER JOIN FETCH doc.grupoDoc where doc.id = :ID")
				.setParameter("ID", id).getSingleResult();

		return resultado;
	}


	/** OBTER O ULTIMO REGISTRO ATIVO
	 * @return */
	public Documento consultaUltimoRegistroAtivo() {
		String query = "from Documento c where "
				+ " c.id = (select max(d.id) from Documento d where d.estatus = :estatus)";
		
		Query q = getManager().createQuery(query);
		q.setParameter("estatus", StatusEntidadeEnum.ATIVO);
		return (Documento) q.getSingleResult();
	}

	/** METODO UTULIZADO SOMENTE PELA TELA CONSULTA-PUBLICAÇÃO - FAZ A PESQUISA PELO FILTRO DO ANO
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	// @SuppressWarnings("unchecked")
	// public List<Documento> consultarPorAno(FiltroPesquisaPadrao filtro)
	// throws ObjetoNaoEncontradoException {
	//
	// List<Documento> resultado = (List<Documento>) getManager()
	// .createQuery("Select doc From Documento doc where year(doc.dtaCadastro) = :ano And status =
	// :ativo ")
	// .setParameter("ano", Integer.parseInt(filtro.getAno()) )
	// .setParameter("ativo", StatusEntidadeEnum.ATIVO ).getResultList();
	//
	// if (resultado.size() == 0) {
	// LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
	// "Nenhum registro encontrado para a pesquisa");
	// throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
	// }
	// return resultado;
	//
	// }

}
