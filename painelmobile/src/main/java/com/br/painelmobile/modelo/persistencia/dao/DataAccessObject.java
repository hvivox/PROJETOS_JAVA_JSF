package com.br.painelmobile.modelo.persistencia.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.NonUniqueResultException;
import org.hibernate.PropertyValueException;
import org.hibernate.QueryException;
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import com.br.painelmobile.modelo.negocios.excecao.ConsultaInvalidaException;
import com.br.painelmobile.modelo.negocios.excecao.EntityIdNuloException;
import com.br.painelmobile.modelo.negocios.excecao.ErroQueryException;
import com.br.painelmobile.modelo.negocios.excecao.MultiplosResultadosParaRecuperarObjetoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.painelmobile.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.painelmobile.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.painelmobile.modelo.persistencia.entidade.enums.EstatusEntidadeEnum;
import com.br.painelmobile.modelo.persistencia.entidade.mapeadas.ValueObject;

//<T extends ValueObject> permite passar através do construtor apenas classes que extendem ValueObject
public abstract class DataAccessObject<T extends ValueObject> implements
		Serializable {
	private static final long serialVersionUID = 4492726696766644916L;
	protected EntityManager manager;
	private final Class<T> persistentClass;
		
	private static final String msgObjetoNaoEncontrado = "Nenhum objeto encontrado para a pesquisa";
	

	
	public DataAccessObject(Class<T> classe, EntityManager manager) {
		// Comando para ajudar na genalização das classes
		this.persistentClass = classe;
		// Atribui o manager já injetado
		this.manager = manager;
	}

	
	// PEGAR UMA INSTANCIA DE QUALQUER TIPO DE DADO
	protected Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	
	public T consultaPorId(Integer id) {
		return manager.find(getPersistentClass(), id);
	}
	

	
	public T consultaObjeto(T vo) throws ObjetoNaoEncontradoException,
			EntityIdNuloException {
		// Isso só é possivel porqute T extend o ValueObject
		return recuperarObjeto(vo.getId());
	}
	
	
	
	
	
	
	public T recuperarObjeto(Integer id)
			throws ObjetoNaoEncontradoException, EntityIdNuloException {

		if (id == null){
			throw new EntityIdNuloException("O id está nulo para a classe "
					+ getPersistentClass().getSimpleName());
		}
		
		T resultado = null;
		resultado = getManager().find(getPersistentClass(), id);

		if (resultado == null) {
			throw new ObjetoNaoEncontradoException("O objeto "
					+ (getPersistentClass()).getSimpleName() + " com id = "
					+ id + "\n Não foi encontrado.");
		}
		return resultado;
	}

	
	
	// RETORNA UM APENAS UM OBJETO
	@SuppressWarnings("unchecked")
	public T recuperarObjetoPorSQL(String stringSQL, String[] params,
			Object[] valores) 
			throws MultiplosResultadosParaRecuperarObjetoException,
			ObjetoNaoEncontradoException, ConsultaInvalidaException {
		T resultado = null;
		
		try {
			Query q = getManager().createNamedQuery(stringSQL);
			int i = 0;
			while (i < params.length) {
				q.setParameter(params[i], valores[i]);
				i++;
			}

			resultado = (T) q.getSingleResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			String msg = "Mais de um valor encontrado para a consulta informada.\n"
					+ "Consulta : " + stringSQL + "\n" + "Parametros : ";
			int j = 0;
			while (j < params.length) {
				msg += params[j] + " = " + valores[j] + "\n";
				j++;
			}
			throw new MultiplosResultadosParaRecuperarObjetoException(msg);
		} catch (SQLGrammarException e) {
			e.printStackTrace();
			throw new ConsultaInvalidaException("Erro na consulta informada!\n"
					+ stringSQL);
		}
		if (resultado == null)
			throw new ObjetoNaoEncontradoException(
					"Nenhum objeto foi encontrado");

		return resultado;
	}

	
	// PESQUISA COM RESTRIÇÕES RETORNA APENAS UM OBJETO
	@SuppressWarnings("unchecked")
	public T recuperarObjetoPorHQL(String stringHQL, String[] params,
			Object[] valores)
			throws MultiplosResultadosParaRecuperarObjetoException,
			ObjetoNaoEncontradoException, ConsultaInvalidaException {
		int i = 0;
		T retorno = null;
		try {

			Query q = getManager().createQuery(stringHQL);

			while (i < params.length) {
				q.setParameter(params[i], valores[i]);
				i++;
			}

			retorno = (T) q.getSingleResult();

		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			String msg = "Mais de um valor encontrado para a consulta informada.\n"
					+ "Consulta : " + stringHQL + "\n" + "Parametros : ";
			int j = 0;
			while (j < params.length) {
				msg += params[j] + " = " + valores[j] + "\n";
				j++;
			}
			throw new MultiplosResultadosParaRecuperarObjetoException(msg);
		} catch (QuerySyntaxException e) {
			e.printStackTrace();
			throw new ConsultaInvalidaException("Erro na consulta informada!\n"
					+ stringHQL);
		}

		if (retorno == null) {
			throw new ObjetoNaoEncontradoException(msgObjetoNaoEncontrado);
		}
		
		return retorno;
	}

	
	
	
	// PREPARA A CONSULTA PARA SER PERQUISADA POR NOME E RETORNA OS DADOS
	public List<T> ListarPorColunaTodosAtivosInativos(String colunaWhere,
			String[] parametro, Object[] dadoEntrada, String colunaOrderby)
			throws ObjetoNaoEncontradoException,
			MultiplosResultadosParaRecuperarObjetoException,
			ConsultaInvalidaException, ErroQueryException {

		List<T> resultado = null;

		String hql = "from " + persistentClass.getSimpleName()
				+ " obj where obj." + colunaWhere + " like :dado order by obj."
				+ colunaOrderby;

		resultado = ListarNomeHql(hql, parametro, dadoEntrada);

		return resultado;
	}

	
	
	
	public List<T> ListarPorColunaSomenteAtivos(String colunaWhere,
			String[] parametro, Object[] dadoEntrada, String colunaOrderby)
			throws ObjetoNaoEncontradoException,
			MultiplosResultadosParaRecuperarObjetoException,
			ConsultaInvalidaException, ErroQueryException {
		List<T> resultado = null;

		String hql = "from " + persistentClass.getSimpleName()
				+ " obj where obj." + colunaWhere
				+ " like :dado and obj.situacao = 'A' order by obj."
				+ colunaOrderby;

		resultado = ListarNomeHql(hql, parametro, dadoEntrada);

		return resultado;
	}

	
	
	
	// LISTA REGISTRO POR NOME COM RESTRIÇÕES: recebe uma consulta pronta do
	// ListarPorNome e impede que uma consulta seja amarrada
	// a estes comandos
	@SuppressWarnings("unchecked")
	public List<T> ListarNomeHql(String stringHQL, String[] params,
			Object[] valores)
			throws MultiplosResultadosParaRecuperarObjetoException,
			ObjetoNaoEncontradoException, ConsultaInvalidaException,
			ErroQueryException {
		int i = 0;
		List<T> retorno = null;

		try {
			Query q = getManager().createQuery(stringHQL);

			while (i < params.length) {
				q.setParameter(params[i], valores[i]);
				i++;
			}
			retorno = q.getResultList();

			if ((retorno.size() == 0)) {
				throw new ObjetoNaoEncontradoException(msgObjetoNaoEncontrado);
			}

			return retorno;
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			String msg = "Mais de um valor encontrado para a consulta informada.\n"
					+ "Consulta : " + stringHQL + "\n" + "Parametros : ";
			int j = 0;
			while (j < params.length) {
				msg += params[j] + " = " + valores[j] + "\n";
				j++;
			}
			throw new MultiplosResultadosParaRecuperarObjetoException(msg);

		} catch (QuerySyntaxException e) {
			e.printStackTrace();
			throw new ConsultaInvalidaException("Erro na consulta informada!\n"
					+ stringHQL);

		} catch (QueryException e) {
			e.printStackTrace();
			throw new ErroQueryException();

		}
	}

	
	
	// PESQUISA TODOS OS OBJETOS SEM RESTRIÇÕES
	public List<T> recuperarObjetos(
			boolean... isLancarExcecaoObjetoNaoEncontrado)
			throws ObjetoNaoEncontradoException {

		List<T> resultado = null;

		resultado = getManager().createQuery(
				"from " + persistentClass.getSimpleName(), persistentClass)
				.getResultList();

		// resultado = manager.createCriteria(persistentClass).list();

		if (resultado.size() == 0) {
			throw new ObjetoNaoEncontradoException(
					"A tabela do banco de Dados "
							+ persistentClass.getSimpleName()
							+ "\n está vazia!");
		}
		return resultado;
	}

	
	// PESQUISA OBJETOS DE SITUACAO ATIVA
	public List<T> recuperarObjetosAtivos(
			boolean... isLancarExcecaoObjetoNaoEncontrado)
			throws ObjetoNaoEncontradoException {

		CriteriaBuilder cb = getManager().getCriteriaBuilder();

		CriteriaQuery<T> result = cb.createQuery(persistentClass);
		Root<T> objetoMultante = result.from(persistentClass);

		Predicate pp = cb.equal(objetoMultante.get("estatus"),
				EstatusEntidadeEnum.ATIVO);
		result.select(objetoMultante).where(pp);

		TypedQuery<T> theQuery = getManager().createQuery(result);
		List<T> resultado = theQuery.getResultList();

		if (resultado.size() == 0) {
			throw new ObjetoNaoEncontradoException(
					"A tabela do banco de Dados: "
							+ persistentClass.getSimpleName()
							+ "\n está vazia!");
		}
		return resultado;
	}

	
	
	
	// LISTA OBJETOS PARA A PAGINACAO
	/**
	 * resultado para paginar o conteudo
	 * @param firstResult numero do registro que deseja iniciar
	 * @param maxResults quantidade de registros por consulta
	 * @return
	 * @throws ObjetoNaoEncontradoException
	 * @throws ConsultaInvalidaException
	 */
	@SuppressWarnings("unchecked")
	public List<T> recuperarPorPaginacao(int firstResult, int maxResults)
			throws ObjetoNaoEncontradoException, ConsultaInvalidaException {

		@SuppressWarnings("unused")
		List<T> listaPaginacao = null;
		Query query = getManager().createQuery("from " + persistentClass.getSimpleName());
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		
		
		return listaPaginacao = query.getResultList();
	}

	
	/**
	 * retorna o total de registros
	 * @return Long total registros
	 * @throws ObjetoNaoEncontradoException
	 */
	public int totalRegistros() throws ObjetoNaoEncontradoException {
    	Long resultado = new Long(0L);
    	Query query = getManager().createQuery("select count(e) from " + persistentClass.getName()+" e" );
    	resultado = (Long) query.getSingleResult();
    	
		
    	if(resultado==0 || resultado==null){
    		throw new ObjetoNaoEncontradoException(msgObjetoNaoEncontrado);
    	}
    	
    	return resultado.intValue();
    }
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<T> recuperarObjetosPorHQL(String stringHQL, String[] params,
			Object[] valores, boolean... isLancarExcecaoObjetoNaoEncontrado)
			throws ObjetoNaoEncontradoException, ConsultaInvalidaException {
		int i = 0;
		List<T> retorno = null;
		try {
			Query q = getManager().createQuery(stringHQL);
			if (params != null) {
				while (i < params.length) {
					q.setParameter(params[i], valores[i]);
					i++;
				}
			}
			retorno = q.getResultList();

			if ((retorno.size() == 0)
					&& (isLancarExcecaoObjetoNaoEncontrado != null)
					&& (isLancarExcecaoObjetoNaoEncontrado[0])) {
				throw new ObjetoNaoEncontradoException(msgObjetoNaoEncontrado);
			}

		} catch (QuerySyntaxException e) {
			e.printStackTrace();
			throw new ConsultaInvalidaException("Erro na consulta informada!\n"
					+ stringHQL);
		}

		return retorno;
	}

	
	
	
	// O FILTRO SE ENCARREGA DE ABRIR E COMITAR A TRANSAÇÃO
	public void removerObjetoPorObjeto(T vo) throws ObjetoNaoEncontradoException,
			EntityIdNuloException {
		T obj = consultaObjeto(vo);
		 getManager().remove(obj);
	}

	

		/**
		 * REMOVER OBJETO VO POR ID
		 * @param id
		 * @throws ObjetoNaoEncontradoException
		 * @throws EntityIdNuloException
		 */
		public void removerObjetoPorId(Integer id) throws ObjetoNaoEncontradoException,
				EntityIdNuloException {
			System.out.println("removendo inicio..............");
			if (id == null){
				throw new EntityIdNuloException("O id está nulo para a classe "
						+ getPersistentClass().getSimpleName());
			}
			
			T resultado = null;
			resultado = getManager().find(getPersistentClass(), id);

			if (resultado == null) {
				throw new ObjetoNaoEncontradoException("O objeto "
						+ (getPersistentClass()).getSimpleName() + " com id = "
						+ id + "\n Não foi encontrado.");
			}	
			 
			getManager().remove(resultado);
			System.out.println("removendo fim..............");
		}
		
	
	
	
	public void salvarObjeto(T vo) throws ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, campoObrigatorioNaoPreenchido,
			ObjetoTransienteSendoPersistido {

		try {

			getManager().persist(vo);
			getManager().flush();

		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new ObjetoJaExistenteException(null);
		} catch (PropertyValueException e) {
			e.printStackTrace();
			throw new campoObrigatorioNaoPreenchido(
					"O campo a seguir deve ser preenchido ", e.getEntityName(),
					e.getPropertyName());
		} catch (TransientObjectException e) {
			e.printStackTrace();
			throw new ObjetoTransienteSendoPersistido();
		}

	}

	
	public T salveOrUpdate(T vo) throws ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, campoObrigatorioNaoPreenchido,
			ObjetoTransienteSendoPersistido {

		try {

			T resultado = getManager().merge(vo);
			getManager().flush();

			return resultado;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new ObjetoJaExistenteException(null);
		} catch (PropertyValueException e) {
			e.printStackTrace();
			throw new campoObrigatorioNaoPreenchido(
					"O campo a seguir deve ser preenchido ", e.getEntityName(),
					e.getPropertyName());
		} catch (TransientObjectException e) {
			e.printStackTrace();
			throw new ObjetoTransienteSendoPersistido();
		}

	}

	public void atualizarObjeto(T vo) throws ObjetoNaoEncontradoException,
			ObjetoJaExistenteException, campoObrigatorioNaoPreenchido,
			ObjetoTransienteSendoPersistido {

		try {

			getManager().merge(vo);
			getManager().flush();

		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			throw new ObjetoJaExistenteException(null);
		} catch (PropertyValueException e) {
			e.printStackTrace();
			throw new campoObrigatorioNaoPreenchido(
					"O campo a seguir deve ser preenchido ", e.getEntityName(),
					e.getPropertyName());
		} catch (TransientObjectException e) {
			e.printStackTrace();
			throw new ObjetoTransienteSendoPersistido();
		}

	}

	protected EntityManager getManager() {
		return manager;
	}

}