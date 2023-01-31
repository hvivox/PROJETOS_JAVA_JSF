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
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.Imagem;

public class DaoImagem extends DataAccessObject<Imagem> {

	private static final long serialVersionUID = 1L;


	@Inject
	public DaoImagem(EntityManager manager) {
		super(Imagem.class, manager);
	}


	/** UTILIZADO PELA TELA DE CONSULTA FILTA INFORMAÇÕES DE ACORDO COM PARAMENTROS INFORMADOS
	 * @param filtro
	 * @return
	 * @throws ObjetoNaoEncontradoException */
	@SuppressWarnings("unchecked")
	public List<Imagem> consultarPorFiltrados(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Imagem.class);

		if (StringUtils.isNotBlank(filtro.getTitulo())) {
			criteria.add(Restrictions.ilike("titulo", filtro.getTitulo(), MatchMode.ANYWHERE));
		}

		if (filtro.isMostraInitivos() == false) {
			criteria.add(Restrictions.eq("estatus", EstatusEntidadeEnum.ATIVO));
		}

		List<Imagem> resultado = criteria.addOrder(Order.asc("titulo")).list();

		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}
		return resultado;
	}


	@SuppressWarnings("unchecked")
	public List<Imagem> consultarImagensPorGaleria(Integer idGaleria) throws ObjetoNaoEncontradoException {
		List<Imagem> resultado = (List<Imagem>) getManager()
				.createQuery("select i from GaleriaImagem g JOIN g.imagens i where g.id = ?")
				.setParameter(1, idGaleria).getResultList();
		
		if (resultado.size() == 0) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn(
					"Nenhum registro encontrado para a pesquisa");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}		
		return resultado;
		
	}

	
	
}
