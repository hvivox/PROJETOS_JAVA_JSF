package com.br.sdni.modelo.persistencia.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.br.sdni.modelo.persistencia.dao.filter.UsuarioFilter;
import com.br.sdni.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.sdni.modelo.persistencia.entidade.mapeadas.Usuario;

public class DaoUsuario extends DataAccessObject<Usuario> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoUsuario(EntityManager manager) {
		super(Usuario.class, manager);
	}


	/* BUSCA FILTRADA------------------------------------------------------------------ */

	@SuppressWarnings("deprecation")
	private Criteria criarCriteriaParaFiltro(UsuarioFilter filtro) {

		Session session = (Session) getManager();
		Criteria criteria = session.createCriteria(Usuario.class);

				// IMPORTANTE: utilizado para ordenação com join o sortby da tela só
				// encontra se explicitar o alias conforme o nome da class
				//.createAlias("grupoDoc", "grupoDoc");

		if (StringUtils.isNotBlank(filtro.getNome())) {
			// PODE SE NECESSÁRIO TROCA O NOME DO CAMPO DE PESQUIA "TITULO"
			criteria.add(Restrictions.ilike("nomeCompleto", filtro.getNome(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInativos() == false) {
			criteria.add(Restrictions.eq("estatus", StatusEntidadeEnum.ATIVO));
		}

		return criteria;
	}


	@SuppressWarnings("unchecked")
	public List<Usuario> consultarPorFiltrados(UsuarioFilter filtro) {

		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		}
		else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		List<Usuario> resultado = criteria.list();

		return resultado;
	}


	/** METODO RETORNA A QUANTIDADE DE TOTAL DE OBJETOS CONSIDERANDO TAMBÉM O FILTRO DOS CAMPOS DE
	 * PESQUISA
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	public int quantidadeFiltrados(UsuarioFilter filtro) {

		// NAO PODE DEIXAR ESSA CHAMADA DE FORA
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	
	/* FIM - BUSCA
	 * FILTRADA-------------------------------------------------------------------------------------
	 * ----- */


	/** VERIFICA SE O USUÁRIO PESQUISADO EXISTE NO BANCO
	 * @param loginUsuario
	 * @return */
	public Usuario consultaLoginUsuario(String loginUsuario) {
		Usuario usuario = null;

		try {
			usuario = this.manager.createQuery(
					"from Usuario u INNER JOIN FETCH u.grupos  where lower(loginUsuario) = :loginUsuario AND estatus='ATIVO'",
					Usuario.class).setParameter("loginUsuario", loginUsuario.toLowerCase())
					.getSingleResult();
		} catch (NoResultException e) {
			// nenhum usuário encontrado com o e-mail informado
		}
		return usuario;
	}


	public Usuario porEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = this.manager
					.createQuery(
							"from Usuario INNER JOIN FETCH u.grupos where lower(email) = :email",
							Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}

		return usuario;
	}


	public String buscarSenha(Integer id) {
		return this.manager.createQuery("select senha from Usuario where id = :id", String.class)
				.setParameter("id", id).getSingleResult();
	}


	/* CONSULTA USUARIO IGUAL A CONSULTA A BAIXO A DIFERENÇA É QUE ESTÁ EM CRITÉRIA */
/*	public List<Usuario> filtrados(UsuarioFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteriaQuery = builder.createQuery(Usuario.class);
		List<Predicate> predicates = new ArrayList<>();

		Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);

		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(usuarioRoot.get("nomeCompleto")),
					"%" + filtro.getNome().toLowerCase() + "%"));
		}

		criteriaQuery.select(usuarioRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(usuarioRoot.get("nomeCompleto")));

		TypedQuery<Usuario> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
*/
	/** UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	/* @SuppressWarnings("unchecked") public List<Usuario>
	 * consultarPorFiltrados(FiltroPesquisaPadrao filtro) throws ObjetoNaoEncontradoException {
	 * Session session = (Session) getManager(); Criteria criteria =
	 * session.createCriteria(Usuario.class);
	 * 
	 * if (StringUtils.isNotBlank(filtro.getTitulo())) { criteria.add(Restrictions.ilike("titulo",
	 * filtro.getTitulo(), MatchMode.ANYWHERE)); }
	 * 
	 * if (filtro.isMostraInativos() == false) { criteria.add(Restrictions.eq("estatus",
	 * StatusEntidadeEnum.ATIVO)); }
	 * 
	 * List<Usuario> resultado = criteria.addOrder(Order.asc("titulo")).list();
	 * 
	 * if (resultado.size() == 0) { LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).
	 * warn("Nenhum registro encontrado para a pesquisa"); throw new
	 * ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa"); } return
	 * resultado; } */

}
