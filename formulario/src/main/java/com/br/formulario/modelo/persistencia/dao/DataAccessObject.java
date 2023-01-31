package com.br.formulario.modelo.persistencia.dao;

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
import org.hibernate.hql.internal.ast.QuerySyntaxException;

import com.br.formulario.modelo.negocios.excecao.ConsultaInvalidaException;
import com.br.formulario.modelo.negocios.excecao.EntityIdNuloException;
import com.br.formulario.modelo.negocios.excecao.ErroQueryException;
import com.br.formulario.modelo.negocios.excecao.MultiplosResultadosParaRecuperarObjetoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoJaExistenteException;
import com.br.formulario.modelo.negocios.excecao.ObjetoNaoEncontradoException;
import com.br.formulario.modelo.negocios.excecao.ObjetoTransienteSendoPersistido;
import com.br.formulario.modelo.negocios.excecao.campoObrigatorioNaoPreenchido;
import com.br.formulario.modelo.persistencia.entidade.enums.StatusEntidadeEnum;
import com.br.formulario.modelo.persistencia.entidade.mapeadas.ValueObject;

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

	
	// PESQUISA PESQUISA OBJETO COM SITUAÇÃO ATIVA
	public List<T> recuperarObjetosAtivos()
			throws ObjetoNaoEncontradoException {

		CriteriaBuilder cb = getManager().getCriteriaBuilder();

		CriteriaQuery<T> result = cb.createQuery(persistentClass);
		Root<T> objetoMultante = result.from(persistentClass);

		Predicate pp = cb.equal(objetoMultante.get("status"),
				StatusEntidadeEnum.ATIVO);
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