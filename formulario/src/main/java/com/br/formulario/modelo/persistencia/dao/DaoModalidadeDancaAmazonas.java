package com.br.formulario.modelo.persistencia.dao;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.LogFactory;

import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.persistencia.dao.filter.FiltroPesquisaPadrao;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.Modalidade;
import com.br.formulario.util.manipularDados.ConversorData;

public class DaoModalidadeDancaAmazonas extends DataAccessObject<Modalidade> {

	private static final long serialVersionUID = 1L;

	@Inject
	public ConversorData conversorData;


	@Inject
	public DaoModalidadeDancaAmazonas(EntityManager manager) {
		super(Modalidade.class, manager);
	}


	/* select nome, count(danca.idModalidadeDancaAmazonas) as contador from DancaAmazonas danca
	 * right join modalidadedancaamazonas moda on danca.idModalidadeDancaAmazonas =
	 * moda.idModalidadeDancaAmazonas and danca.dtaInscricao >= '2017-05-06' where moda.status =
	 * 'ATIVO' || danca.idModalidadeDancaAmazonas is null group by moda.nome order by nome; 	*/

	@SuppressWarnings("unchecked")
	public List<Object[]> consultarDadosInscricao(FiltroPesquisaPadrao filtro)
			throws ObjetoNaoEncontradoException {
		
		Calendar dataInscricao = conversorData.converterStringEmCalendar(filtro
				.getDtaInicioInscricao());

		List<Object[]> listaDadosInscricao = null;
		Query query = getManager().createQuery(
				"select mod, count(danca) as contador "
						+ "FROM DancaAmazonas danca RIGHT JOIN danca.modalidade mod WITH "
						+ "(danca.dtaInscricao >=:dtaInscricaoParametro) " + "WHERE  "
						+ "mod.status =:statusParametro " + "GROUP BY nome "
						+ "ORDER BY mod.nome ASC");

		query.setParameter("dtaInscricaoParametro", dataInscricao);
		query.setParameter("statusParametro", StatusEntidadeEnum.ATIVO);

		listaDadosInscricao = query.getResultList();

		/*for (Object[] objects : listaDadosInscricao) { Long contador = (Long) objects[1];
		 * System.out.println("Contador: "+contador); } */

		if (listaDadosInscricao == null || listaDadosInscricao.size() == 0
				|| !(listaDadosInscricao instanceof Object)) {
			LogFactory.getLog(Logger.GLOBAL_LOGGER_NAME).warn("Não foi encontradas mensagens.....");
			throw new ObjetoNaoEncontradoException("Nenhum registro encontrado para a pesquisa");
		}

		return listaDadosInscricao;
	}

}
